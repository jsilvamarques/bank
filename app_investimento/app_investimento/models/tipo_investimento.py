from sqlalchemy import (
    Column,
    Integer,
    String,
    Boolean,
)

from app_investimento.database.database import DeclarativeBase


class TipoInvestimento(DeclarativeBase):
    __tablename__ = "tipo_investimento"

    id = Column(Integer, primary_key=True)
    nome = Column(String)
    ativo = Column(Boolean, default=True)
