import asyncio

from langchain.agents import create_agent
from langchain.messages import HumanMessage
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_ollama import ChatOllama


async def main():

    client = MultiServerMCPClient(
        {
            "local_server": {
                "transport": "stdio",
                "command": "python",
                "args": ["resources/mcp_local_server.py"],
            }
        }
    )

    tools = await client.get_tools()

    resources = await client.get_resources("local_server")

    prompt = await client.get_prompt("local_server", "prompt")
    prompt = prompt[0].content
    # Add a strict formatting rule for the small model
    prompt += "\nCRITICAL: When calling tools, your arguments must be plain text strings or values. Do NOT output type definitions like '{'type': 'string'}' as the argument value."

    model = ChatOllama(model="llama3.2:3b", temperature=0)

    agent = create_agent(model=model, tools=tools, system_prompt=prompt)

    config = {"configurable": {"thread_id": "1"}}
    response = await agent.ainvoke(
        {
            "messages": [
                HumanMessage(content="Tell me about the langchain-mcp-adapters library")
            ]
        },
        config=config,
    )
    # --- REPLACE print(response) WITH THIS ---
    print("\n" + "=" * 60)
    print("🚀 AGENTIC EXECUTION TRACE")
    print("=" * 60)

    for msg in response.get("messages", []):
        if msg.type == "human":
            print(f"\n👤 [USER]: {msg.content}")

        elif msg.type == "ai":
            if msg.tool_calls:
                for tool in msg.tool_calls:
                    # Extracts the search term the agent generated
                    q = tool["args"].get("query", tool["args"])
                    print(
                        f"🤖 [AGENT]: Calling tool '{tool['name']}' using query: \"{q}\""
                    )
            if msg.content:
                print(f"\n🤖 [AGENT FINAL ANSWER]:\n{msg.content}")

        elif msg.type == "tool":
            print("🛠️ [TOOL COMPLETED]: Context successfully injected into model.")

    print("\n" + "=" * 60)


asyncio.run(main())
