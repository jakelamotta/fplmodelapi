import asyncio
import json
import sys

import aiohttp

from understat import Understat


async def main(gameId):
    async with aiohttp.ClientSession(cookies={'beget':'begetok'}) as session:
        understat = Understat(session)
        data = await understat.get_match_players(gameId)

        print(json.dumps(data))


if __name__ == "__main__":
    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    loop.run_until_complete(main(sys.argv[1]))
