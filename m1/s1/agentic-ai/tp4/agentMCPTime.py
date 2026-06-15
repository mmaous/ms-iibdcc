import asyncio

from langchain.agents import create_agent
from langchain.messages import HumanMessage
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_ollama import ChatOllama


async def main():
    # Configuration et initialisation d'un client MCP pour un serveur de temps
    client = MultiServerMCPClient(
        {
            "time": {
                "transport": "stdio",
                "command": "uvx",
                "args": ["mcp-server-time", "--local-timezone=America/New_York"],
            }
        }
    )

    # Récupération dynamique des tools
    tools = await client.get_tools()

    # Initialiser le modèle Ollama
    model = ChatOllama(
        model="llama3.2:3b",
    )

    # Création et exécution d'un agent LLM
    agent = create_agent(
        model=model,
        tools=tools,
    )

    question = HumanMessage(content="What time is it in Japan")
    response = await agent.ainvoke({"messages": [question]})
    print(response["messages"][-1].content)


asyncio.run(main())
