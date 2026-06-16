import time
import uuid

from langgraph.checkpoint.memory import InMemorySaver
from langgraph.func import entrypoint, task
from langgraph.types import Command, interrupt


# 1. Définition d'une tâche isolée (Task)
@task
def write_essay(topic: str) -> str:
    """Simule la génération d'un texte/essai."""
    time.sleep(1)  # Simule un traitement
    return f"Essay draft about {topic}"


# 2. Définition du flux principal avec un checkpointer pour la persistance
@entrypoint(checkpointer=InMemorySaver())
def workflow(topic: str) -> dict:
    """Orchestre les tâches et gère l'interruption humaine."""

    # Appel de la tâche et récupération immédiate du résultat via .result()
    draft = write_essay(topic).result()

    # L'exécution s'arrête net ici (Pause). Elle envoie un payload à l'extérieur.
    # La variable 'approved' recevra la valeur passée lors du 'resume'.
    approved = interrupt({"draft": draft, "action": "approve or reject"})

    return {"draft": draft, "approved": approved}


if __name__ == "__main__":
    # Génération d'un identifiant unique pour suivre cette session de conversation (Thread)
    thread_id = str(uuid.uuid4())
    config = {"configurable": {"thread_id": thread_id}}

    print("--- PREMIÈRE EXÉCUTION (Lancement et Interruption) ---")
    # Le workflow démarre, génère le draft, puis rencontre 'interrupt()' et s'arrête.
    for item in workflow.stream("cats", config):
        print("Stream Out:", item)

    print("\n--- DEUXIÈME EXÉCUTION (Reprise / Resume) ---")
    # On relance le workflow sur le MÊME thread en envoyant un Command(resume=...)
    # Ici, l'utilisateur humain valide l'essai (True)
    for item in workflow.stream(Command(resume=True), config):
        print("Stream Out:", item)
