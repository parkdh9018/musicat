import logging

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