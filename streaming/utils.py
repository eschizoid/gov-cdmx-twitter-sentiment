"""This file contains some utilities"""

from oauth2client.client import GoogleCredentials

SCOPES = ['https://www.googleapis.com/auth/pubsub']
NUM_RETRIES = 3


def get_credentials():
    """Get the Google credentials needed to access our services."""
    credentials = GoogleCredentials.get_application_default()
    if credentials.create_scoped_required():
        credentials = credentials.create_scoped(SCOPES)
    return credentials
