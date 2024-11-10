import os
import logging
from logging.handlers import SysLogHandler
from dotenv import find_dotenv, load_dotenv

dotenv_path = find_dotenv()
load_dotenv(dotenv_path=dotenv_path)

PAPERTRAIL_HOST = os.getenv("PAPERTRAIL_HOST")
PAPERTRAIL_PORT = int(os.getenv("PAPERTRAIL_PORT"))

logger = logging.getLogger("infoLog")
logger.setLevel(logging.INFO)
handler = SysLogHandler(address=(PAPERTRAIL_HOST,PAPERTRAIL_PORT))
formatter = logging.Formatter('%(asctime)s Logging@Module MultimediaAPI : %(levelname)s[%(name)s] %(message)s', datefmt='%b %d %H:%M:%S')

handler.setLevel(logging.INFO)
handler.setFormatter(formatter)
logger.addHandler(handler)

def logInfo(message) -> None :
    logger.info(message)

if __name__ == '__main__':
    logInfo("Testing logging from python")
    print("Hello")