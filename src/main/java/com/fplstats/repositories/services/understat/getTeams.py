import asyncio
import json
import sys
import aiohttp

from understat import Understat


async def main(leagueName, year):
    async with aiohttp.ClientSession() as session:
        understat = Understat(session)
        data = await understat.get_teams(leagueName, year)

        print(json.dumps(data))
        

if __name__ == "__main__":

    #print()

    loop = asyncio.get_event_loop()
    loop.run_until_complete(main(sys.argv[1], sys.argv[2]))
