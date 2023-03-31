from aiokafka import AIOKafkaConsumer, AIOKafkaProducer
import json
import datetime
import logic_story
import logic_chat
import logic_music
import logic_test
import radio_progress
from shared_state import radio_health, chat_readable
from my_logger import setup_logger

logger = setup_logger()

kafka_servers = ["host.docker.internal:9092", "host.docker.internal:9093", "host.docker.internal:9094"]

##############################################

async def consume_finish_state(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            if radio_health.get_state() is True:
                await radio_progress.radio_progress()
            else:
                await logic_test.radio_progress_test()
    finally:
        await consumer.stop()

async def consume_finish_chat(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            chat_readable.set_state(False)
    finally:
        await consumer.stop()

##############################################

async def consume_verify_story(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            await logic_story.process_verify_story_data(msg.value)
    finally:
        await consumer.stop()

##############################################

async def consume_chat(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            await logic_chat.process_chat_data(msg.value)
    finally:
        await consumer.stop()

##############################################

async def consume_music(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            await logic_music.process_music_data(msg.value)
    finally:
        await consumer.stop()

##############################################

class DateTimeEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, datetime.datetime):
            return obj.isoformat()
        return super(DateTimeEncoder, self).default(obj)

async def send_state(topic: str, message):
    producer = AIOKafkaProducer(
        bootstrap_servers=kafka_servers,
        value_serializer=lambda m: json.dumps(m, cls=DateTimeEncoder).encode("utf-8")
    )
    await producer.start()
    try:
        await producer.send_and_wait(topic, message)
    finally:
        await producer.stop()

##############################################