from aiokafka import AIOKafkaConsumer, AIOKafkaProducer
import json
from datetime import datetime
import story_logic
import chat_logic
import music_logic
import radio_progress
from shared_state import radio_health
import test_logic

kafka_servers = ["host.docker.internal:9092", "host.docker.internal:9093", "host.docker.internal:9094"]

async def consume_finish_state(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            print(msg.value)
            if radio_health.get_state() is True:
                await radio_progress.radio_progress()
            else:
                await test_logic.radio_progress_test()
    finally:
        await consumer.stop()

async def consume_verify_story(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            print(msg.value)
            await story_logic.process_verify_story_data(msg.value)
    finally:
        await consumer.stop()

        
async def consume_chat(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            print(msg.value)
            await chat_logic.process_chat_data(msg.value)
    finally:
        await consumer.stop()

async def consume_music(topic: str):
    consumer = AIOKafkaConsumer(
        topic,
        bootstrap_servers=kafka_servers,
        value_deserializer=lambda m: json.loads(m.decode("utf-8"))
    )
    await consumer.start()
    try:
        async for msg in consumer:
            print(msg.value)
            await music_logic.process_music_data(msg.value)
    finally:
        await consumer.stop()

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