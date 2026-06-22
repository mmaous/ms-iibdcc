import csv
import json
import os
import pendulum
from airflow import DAG
from airflow.operators.python import PythonOperator

DATA_DIR = "/opt/airflow/data"
RAW_FILE = f"{DATA_DIR}/etudiants_raw.csv"
CLEAN_FILE = f"{DATA_DIR}/etudiants_clean.csv"
GROUPS_FILE = f"{DATA_DIR}/etudiants_groupes.json"
STATS_FILE = f"{DATA_DIR}/statistiques_inscriptions.json"
REPORT_FILE = f"{DATA_DIR}/rapport_final_inscriptions.txt"

# 1. Réception du fichier des étudiants
def reception_fichier():
    os.makedirs(DATA_DIR, exist_ok=True)
    # Mock data for students
    etudiants = [
        ["id_etudiant", "nom", "filiere", "note"],
        [101, " Amine ", "II-BDDC", 15.5],
        [102, "Sara", "II-BDDC", 14.0],
        [103, "Youssef", "G-INFO", 12.5],
        [104, " Layla ", "II-BDDC", 16.0],
        [105, "Hamza", "G-INFO", 11.0],
    ]
    with open(RAW_FILE, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerows(etudiants)
    print(f"Étape 1: Fichier reçu et enregistré dans {RAW_FILE}")

# 2. Stockage du fichier dans une zone brute
def stockage_zone_brute():
    if not os.path.exists(RAW_FILE):
        raise FileNotFoundError("Le fichier brut des étudiants est introuvable.")
    taille = os.path.getsize(RAW_FILE)
    print(f"Étape 2: Fichier stocké avec succès dans la zone brute ({taille} octets).")

# 3. Validation du fichier
def validation_fichier():
    with open(RAW_FILE, mode="r", encoding="utf-8") as file:
        reader = csv.reader(file)
        header = next(reader)
    colonnes_attendues = ["id_etudiant", "nom", "filiere", "note"]
    if header != colonnes_attendues:
        raise ValueError("Le schéma du fichier étudiant est incorrect !")
    print("Étape 3: Validation du schéma réussie.")

# 4. Nettoyage des données (Simulation)
def nettoyage_donnees():
    lignes_nettoyees = []
    with open(RAW_FILE, mode="r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            lignes_nettoyees.append({
                "id_etudiant": row["id_etudiant"].strip(),
                "nom": row["nom"].strip(),  # Removes extra spacing
                "filiere": row["filiere"].strip(),
                "note": float(row["note"])
            })
    with open(CLEAN_FILE, mode="w", newline="", encoding="utf-8") as file:
        fieldnames = ["id_etudiant", "nom", "filiere", "note"]
        writer = csv.DictWriter(file, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(lignes_nettoyees)
    print("Étape 4: Nettoyage des données terminé.")

# 5. Affectation des étudiants aux groupes
def affectation_groupes():
    affectations = []
    with open(CLEAN_FILE, mode="r", encoding="utf-8") as file:
        reader = csv.DictReader(file)
        for row in reader:
            # Simple group assignment logic based on major
            groupe = "Section_A" if row["filiere"] == "II-BDDC" else "Section_B"
            row["groupe"] = groupe
            affectations.append(row)

    with open(GROUPS_FILE, mode="w", encoding="utf-8") as file:
        json.dump(affectations, file, indent=4, ensure_ascii=False)
    print("Étape 5: Affectation aux groupes terminée.")

# 6. Génération de statistiques
def generation_statistiques():
    with open(GROUPS_FILE, mode="r", encoding="utf-8") as file:
        data = json.load(file)

    stats = {"Total_Etudiants": len(data), "Par_Filiere": {}}
    for etudiant in data:
        filiere = etudiant["filiere"]
        stats["Par_Filiere"][filiere] = stats["Par_Filiere"].get(filiere, 0) + 1

    with open(STATS_FILE, mode="w", encoding="utf-8") as file:
        json.dump(stats, file, indent=4, ensure_ascii=False)
    print("Étape 6: Statistiques générées.")

# 7. Génération du rapport final
def generation_rapport_final():
    with open(STATS_FILE, mode="r", encoding="utf-8") as file:
        stats = json.load(file)

    with open(REPORT_FILE, mode="w", encoding="utf-8") as report:
        report.write("Rapport Final des Inscriptions Étudiants\n")
        report.write("=========================================\n")
        report.write(f"Nombre total d'étudiants inscrits : {stats['Total_Etudiants']}\n\n")
        report.write("Répartition par filière :\n")
        for filiere, count in stats["Par_Filiere"].items():
            report.write(f" - {filiere} : {count} étudiants\n")

    print(f"Étape 7: Rapport final généré dans {REPORT_FILE}")

# Definition of the DAG
with DAG(
    dag_id="pipeline_inscription_etudiants",
    start_date=pendulum.datetime(2026, 1, 1, tz="UTC"),
    schedule=None,
    catchup=False,
    tags=["administration", "exercice-final"],
) as dag:

    step1 = PythonOperator(task_id="reception_fichier", python_callable=reception_fichier)
    step2 = PythonOperator(task_id="stockage_zone_brute", python_callable=stockage_zone_brute)
    step3 = PythonOperator(task_id="validation_fichier", python_callable=validation_fichier)
    step4 = PythonOperator(task_id="nettoyage_donnees", python_callable=nettoyage_donnees)
    step5 = PythonOperator(task_id="affectation_groupes", python_callable=affectation_groupes)
    step6 = PythonOperator(task_id="generation_statistiques", python_callable=generation_statistiques)
    step7 = PythonOperator(task_id="generation_rapport_final", python_callable=generation_rapport_final)

    # Linear execution flow
    step1 >> step2 >> step3 >> step4 >> step5 >> step6 >> step7
