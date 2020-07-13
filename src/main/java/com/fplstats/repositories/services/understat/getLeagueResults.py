import asyncio
import json
import sys

import aiohttp

from understat import Understat


async def main(league, year):
    async with aiohttp.ClientSession() as session:
        understat = Understat(session)
        data = await understat.get_league_results(league, year)

        print(json.dumps(data))


if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(main(sys.argv[1], sys.argv[2]))
