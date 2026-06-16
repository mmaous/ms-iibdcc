from operator import add
from typing import Literal

from langchain_core.messages import AnyMessage, HumanMessage, SystemMessage, ToolMessage
from langgraph.graph import END, START, StateGraph
from typing_extensions import Annotated, TypedDict

from tools_setup import model_with_tools, tools_by_name


# Define the overall structure of the Graph State
class AgentState(TypedDict):
    messages: Annotated[
        list[AnyMessage], add
    ]  # 'add' acts as a reducer to append new messages
    llm_calls: int


def llm_call(state: AgentState):
    """LLM node: decides whether to call tools or respond to the user."""
    response = model_with_tools.invoke(
        [
            SystemMessage(
                content="You are a helpful assistant that solves arithmetic problems using tools when needed."
            )
        ]
        + state["messages"]
    )

    return {
        "messages": [response],
        "llm_calls": state.get("llm_calls", 0) + 1,
    }


def tool_node(state: AgentState):
    """Action node: executes the requested tools found in the last AI message."""
    last = state["messages"][-1]
    results = []

    for call in last.tool_calls:
        tool = tools_by_name[call["name"]]
        observation = tool.invoke(call["args"])
        # Results must be wrapped in a ToolMessage pointing back to the tool_call_id
        results.append(ToolMessage(content=str(observation), tool_call_id=call["id"]))

    return {"messages": results}


def should_continue(state: AgentState) -> Literal["tool_node", END]:
    """Conditional Edge function to route control based on tool invocation intent."""
    last = state["messages"][-1]
    if getattr(last, "tool_calls", None):
        if last.tool_calls:
            return "tool_node"
    return END


# Constructing and compiling the State Graph
builder = StateGraph(AgentState)
builder.add_node("llm_call", llm_call)
builder.add_node("tool_node", tool_node)

builder.add_edge(START, "llm_call")
builder.add_conditional_edges("llm_call", should_continue, ["tool_node", END])
builder.add_edge("tool_node", "llm_call")

agent = builder.compile()

if __name__ == "__main__":
    # --- METHOD 1: Standard Sync Invoke ---
    print("=== METHOD 1: Standard Invocation ===")
    result = agent.invoke(
        {"messages": [HumanMessage(content="Add 3 and 4.")], "llm_calls": 0}
    )
    print(result)

    for m in result["messages"]:
        try:
            m.pretty_print()  # Formatted visualization of message contents
        except Exception as ex:
            print(ex)
            print(m)

    # --- METHOD 2: Stream updates (State Deltas Node by Node) ---
    print("\n=== METHOD 2: Stream Updates ===")
    for chunk in agent.stream(
        {"messages": [HumanMessage(content="Multiply 30 and 43.")], "llm_calls": 0},
        stream_mode="updates",  # Prints exactly what each node adds/updates in the state
    ):
        print(chunk)

    # --- METHOD 3: Stream messages (Token-by-Token streaming) ---
    print("\n=== METHOD 3: Stream Messages ===")
    for message_chunk, metadata in agent.stream(
        {"messages": [HumanMessage(content="Divide 30 and 43.")], "llm_calls": 0},
        stream_mode="messages",  # Useful for progressive UI token printing
    ):
        if getattr(message_chunk, "content", None):
            print(message_chunk.content, end="", flush=True)
    print()
