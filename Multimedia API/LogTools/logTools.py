import os
import logging
from logging.handlers import SysLogHandler
from dotenv import find_dotenv, load_dotenv

dotenv_path = find_dotenv()
load_dotenv(dotenv_path=dotenv_path)

PAPERTRAIL_HOST = os.getenv("PAPERTRAIL_HOST")
PAPERTRAIL_PORT = int(os.getenv("PAPERTRAIL_PORT"))

def logInfo(message) -> None :
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    handler = SysLogHandler(address=(PAPERTRAIL_HOST,PAPERTRAIL_PORT))
    logger.addHandler(handler)

    logger.debug(message)