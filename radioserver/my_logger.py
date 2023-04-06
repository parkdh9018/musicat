import logging
import time

##############################################

def setup_logger(name: str = "uvicorn"):
    logger = logging.getLogger(name)
    
    if logger.hasHandlers():
        return logger.getChild("my_logger")

    logger.setLevel(logging.INFO)
    handler = logging.StreamHandler()
    formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
    handler.setFormatter(formatter)
    logger.addHandler(handler)
    return logger.getChild("my_logger")

##############################################

def measure_execution_time(func):
    def wrapper(*args, **kwargs):
        start_time = time.time()
        result = func(*args, **kwargs)
        end_time = time.time()
        print(f"{func.__name__} took {end_time - start_time:.5f} seconds to execute.")
        return result
    return wrapper