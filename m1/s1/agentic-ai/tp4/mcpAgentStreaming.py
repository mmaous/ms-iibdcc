import asyncio

from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain.messages import HumanMessage
from langchain_mcp_adapters.client import MultiServerMCPClient

# Import Ollama to bypass OpenAI
from langchain_ollama import ChatOllama
from langgraph.checkpoint.memory import InMemorySaver


async def main():
    # Keep the connection to the remote travel server via HTTP streaming
    client = MultiServerMCPClient(
        {
            "travel_server": {
                "transport": "streamable_http",
                "url": "https://mcp.kiwi.com",
            }
        }
    )

    print("Connecting to Kiwi Travel MCP Server...")
    tools = await client.get_tools()

    load_dotenv()

    # Initialize your local free model
    model = ChatOllama(model="llama3.2:3b", temperature=0)

    # Build the agent using your local model instead of "gpt-5-nano"
    agent = create_agent(
        model=model,
        tools=tools,
        checkpointer=InMemorySaver(),
        system_prompt="You are a travel agent. Provide direct answers. No follow up questions.",
    )

    config = {"configurable": {"thread_id": "1"}}

    print("Invoking agent loop...")
    response = await agent.ainvoke(
        {
            "messages": [
                HumanMessage(
                    content="Get me a direct flight from Rabat to Agadir on August 31st"
                )
            ]
        },
        config=config,
    )

    # --- Clean Formatting Block for Lab Screenshot ---
    print("\n" + "=" * 60)
    print("🚀 PART 3: DISTANT MCP SERVER EXECUTION TRACE")
    print("=" * 60)

    for msg in response.get("messages", []):
        if msg.type == "human":
            print(f"\n👤 [USER]: {msg.content}")

        elif msg.type == "ai":
            if msg.tool_calls:
                for tool in msg.tool_calls:
                    print(f"🤖 [AGENT]: Calling remote Kiwi tool '{tool['name']}'...")
            if msg.content:
                print(f"\n🤖 [AGENT FINAL ANSWER]:\n{msg.content}")

        elif msg.type == "tool":
            print("🛠️ [REMOTE MCP COMPLETED]: Flight options received.")

    print("\n" + "=" * 60)


asyncio.run(main())
