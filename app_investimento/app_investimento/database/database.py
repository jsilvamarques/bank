from databases import Database
from sqlalchemy import create_engine, MetaData
from sqlalchemy.ext.declarative import declarative_base

from app_investimento.config.settings import get_settings

settings = get_settings()

engine = create_engine(settings.DATABASE_URL)
DeclarativeBase = declarative_base()
database = Database(settings.DATABASE_URL)
