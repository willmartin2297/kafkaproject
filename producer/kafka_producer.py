from confluent_kafka import Producer
from uv_index import fetch_uv_data
import json
import time
import logging

logging.basicConfig(format='%(asctime)s %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S',
                    filename='producer.log',
                    filemode='w')

logger = logging.getLogger()
logger.setLevel(logging.INFO)

cities = {
    "belfast": {
        "latitude": "54.66",
        "longitude": "-5.71"
    },
    "london": {
        "latitude": "51.52",
        "longitude": "-0.13"
    },
    "toronto": {
        "latitude": "43.65",
        "longitude": "-79.42"
    },
    "buenos_aires": {
        "latitude": "-34.60",
        "longitude": "-58.38"
    },
    "nairobi": {
        "latitude": "-1.28",
        "longitude": "36.83"
    },
    "nagpur": {
        "latitude": "21.08",
        "longitude": "79.12"
    },
    "tokyo": {
        "latitude": "35.64",
        "longitude": "139.86"
    },
    "brisbane": {
        "latitude": "-27.49",
        "longitude": "153.13"
    }
}


def read_ccloud_config(config_file):
    conf = {}
    with open(config_file) as fh:
        for line in fh:
            line = line.strip()
            if len(line) != 0 and line[0] != "#":
                parameter, value = line.strip().split('=', 1)
                conf[parameter] = value.strip()
    return conf


def receipt(err, msg):
    if err is not None:
        print('Error: {}'.format(err))
    else:
        message = 'Produced message on topic {} with value of {}\n'.format(msg.topic(), msg.value().decode('utf-8'))
        logger.info(message)
        print(message)


def produce_to_topic():
    producer = Producer(read_ccloud_config("client.properties"))
    for city in cities:
        uv_data = json.dumps(fetch_uv_data(cities.get(city).get('latitude'), cities.get(city).get('longitude')))
        producer.poll(1)
        producer.produce('uv_index', key=city, value=uv_data.encode('utf-8'), callback=receipt)
        producer.flush()
        time.sleep(1)


if __name__ == '__main__':
    produce_to_topic()

