import os
import logging
import logging.handlers
from logging.handlers import SysLogHandler

import aspectlib
from dotenv import find_dotenv, load_dotenv

dotenv_path = find_dotenv()
load_dotenv(dotenv_path=dotenv_path)

PAPERTRAIL_HOST = os.getenv('PAPERTRAIL_HOST')
PAPERTRAIL_PORT = int(os.getenv('PAPERTRAIL_PORT'))


def get_papertrail_logger(name: str) -> logging.Logger:
    logger = logging.getLogger("infoLog")
    logger.setLevel(logging.INFO)
    handler = SysLogHandler(address=(PAPERTRAIL_HOST, PAPERTRAIL_PORT))
    formatter = logging.Formatter('%(asctime)s Logging@Module SuggestionAPI : %(levelname)s[%(name)s] %(message)s',
                                  datefmt='%b %d %H:%M:%S')

    handler.setLevel(logging.INFO)
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    return logger


logger = get_papertrail_logger(__name__)


@aspectlib.Aspect
def log_aspect(*args, **kwargs):
    logger.info(f"Calling {__name__} with args: {args}, kwargs: {kwargs}")
    result = yield aspectlib.Proceed
    logger.info(f"{__name__} returned {result}")
    yield aspectlib.Return(result.strip())


@aspectlib.Aspect
def exception_handling_aspect(*args, **kwargs):
    try:
        result = yield aspectlib.Proceed
        yield aspectlib.Return(result.strip())
    except Exception as e:
        logger.error(f"Exception in {__name__}: {e}")
        raise
