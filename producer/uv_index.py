import requests
import time
from calendar import timegm


def fetch_uv_data(latitude, longitude):
    try:
        url = "https://api.openuv.io/api/v1/uv?lat={}&lng={}&alt=100".format(latitude, longitude)
        response = requests.get(url=url, headers={'x-access-token': 'openuv-6fzbrllmozun4-io'})
        return _process_uv_data_response(response.json())
    except AttributeError:
        print("Daily Request Quota Reached")


def _process_uv_data_response(response_json):
    data = response_json.get('result')
    sun_data = data.get("sun_info").get("sun_times")

    return {
        "uv_min": data.get('uv'),
        "uv_min_time": _convert_date_time_to_epoch(data.get('uv_time')),
        "uv_max": data.get('uv_max'),
        "uv_max_time": _convert_date_time_to_epoch(data.get('uv_max_time')),
        "sunrise_time": _convert_date_time_to_epoch(sun_data.get('sunrise')),
        "sunset_time": _convert_date_time_to_epoch(sun_data.get('sunset'))
    }


def _convert_date_time_to_epoch(date_time):
    return timegm(time.strptime(date_time, "%Y-%m-%dT%H:%M:%S.%fZ"))

