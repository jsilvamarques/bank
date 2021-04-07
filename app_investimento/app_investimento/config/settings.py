import os
from functools import lru_cache
from pydantic import BaseSettings, PostgresDsn, Field

ENV_FILE = '.env'


class Settings(BaseSettings):
    DATABASE_URL: PostgresDsn = Field(..., env="bd_url")
    APP_NAME: str = "Investimento APP"
    API_PREFIX: str = "/investimento"
    HOST: str = Field(..., env="host")
    PORT: int = Field(..., env="port")
    BASE_URL: str = f'{HOST}:{str(PORT)}'

    class Config:
        case_sensitive: bool = True


@lru_cache()
def get_settings() -> Settings:
    dir_path = os.path.dirname(os.path.realpath(__file__))
    settings = Settings(
        _env_file=f'{dir_path}/{ENV_FILE}',
        _env_file_encoding='utf-8',
    )
    return settings
