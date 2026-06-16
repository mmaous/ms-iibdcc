from typing import Literal

from IPython.display import Image, display
from langgraph.graph import END, START, StateGraph
from typing_extensions import TypedDict


class State(TypedDict):
    n: int
    log: list[str]


def step(state: State):
    n = state["n"] + 1
    return {"n": n, "log": state["log"] + [f"n is now {n}"]}


def should_continue(state: State) -> Literal["again", "stop"]:
    return "again" if state["n"] < 5 else "stop"


builder = StateGraph(State)
builder.add_node("step", step)
builder.add_edge(START, "step")

builder.add_conditional_edges("step", should_continue, {"again": "step", "stop": END})

graph = builder.compile()
result = graph.invoke({"n": 0, "log": []})
print(result)

# Only runs successfully if you have the proper system dependencies (like Graphviz/Mermaid) installed
try:
    png_bytes = graph.get_graph().draw_mermaid_png()
    with open("graph2.png", "wb") as f:
        f.write(png_bytes)
except Exception as e:
    print("Graph visualization skipped. Ensure Mermaid dependencies are installed.")
