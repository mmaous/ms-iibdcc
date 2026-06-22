import csv, json, os, pendulum
from airflow import DAG
from airflow.operators.python import PythonOperator

DATA_DIR = "/opt/airflow/data"
CLEAN_FILE = f"{DATA_DIR}/ventes_clean.csv"
RESULT_VILLE = f"{DATA_DIR}/resultat_par_ville.json"
RESULT_PRODUIT = f"{DATA_DIR}/resultat_par_produit.json"
REPORT_FILE = f"{DATA_DIR}/rapport_parallel.txt"

def preparation_donnees():
    os.makedirs(DATA_DIR, exist_ok=True)
    ventes = [
        ["id_vente", "ville", "produit", "prix", "quantite", "montant"],
        [1, "Casablanca", "PC", 8000, 2, 16000],
        [2, "Rabat", "Clavier", 300, 5, 1500],
        [3, "Marrakech", "Souris", 150, 10, 1500],
        [4, "Casablanca", "Ecran", 2500, 3, 7500],
        [5, "Tanger", "PC", 8500, 1, 8500],
        [6, "Rabat", "Ecran", 2300, 2, 4600],
    ]
    with open(CLEAN_FILE, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerows(ventes)

def validation_donnees():
    if not os.path.exists(CLEAN_FILE): raise FileNotFoundError("Missing file")

def traitement_par_ville():
    resultats = {}
    with open(CLEAN_FILE, mode="r", encoding="utf-8") as file:
        for row in csv.DictReader(file):
            resultats[row["ville"]] = resultats.get(row["ville"], 0) + float(row["montant"])
    with open(RESULT_VILLE, mode="w", encoding="utf-8") as file: json.dump(resultats, file)


    print(f"Calculs termines pour les villes : {resultats}")


def traitement_par_produit():
    resultats = {}
    with open(CLEAN_FILE, mode="r", encoding="utf-8") as file:
        for row in csv.DictReader(file):
            resultats[row["produit"]] = resultats.get(row["produit"], 0) + float(row["montant"])
    with open(RESULT_PRODUIT, mode="w", encoding="utf-8") as file: json.dump(resultats, file)

def generation_rapport_final():
    with open(RESULT_VILLE, "r") as f1, open(RESULT_PRODUIT, "r") as f2:
        par_ville = json.load(f1)
        par_produit = json.load(f2)
    with open(REPORT_FILE, "w") as report:
        report.write("Rapport final\n==============\nChiffre par ville\n")
        for k, v in par_ville.items(): report.write(f"{k} : {v} DH\n")
        report.write("\nChiffre par produit\n")
        for k, v in par_produit.items(): report.write(f"{k} : {v} DH\n")

with DAG(
    dag_id="pipeline_big_data_parallele", start_date=pendulum.datetime(2026, 1, 1, tz="UTC"), catchup=False
) as dag:
    prep = PythonOperator(task_id="preparation", python_callable=preparation_donnees)
    valid = PythonOperator(task_id="validation", python_callable=validation_donnees)
    analyse_ville = PythonOperator(task_id="par_ville", python_callable=traitement_par_ville)
    analyse_produit = PythonOperator(task_id="par_produit", python_callable=traitement_par_produit)
    rapport = PythonOperator(task_id="rapport_final", python_callable=generation_rapport_final)

    prep >> valid >> [analyse_ville, analyse_produit] >> rapport
