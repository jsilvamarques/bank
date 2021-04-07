import enum
from datetime import date
from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    Enum,
    Date,
)
from sqlalchemy.orm import relationship

from app_investimento.database.database import DeclarativeBase
from app_investimento.models.tipo_investimento import TipoInvestimento
from app_investimento.models.rendimento import Rendimento


class InvestimentoStatus(enum.Enum):

    em_andamento = "Em Andamento"
    cancelado = "Cancelado"
    finalizado = "Finalizado"


class Investimento(DeclarativeBase):
    __tablename__ = "investimento"

    id = Column(Integer, primary_key=True)
    conta = Column(String)
    valor = Column(Float)
    tempo = Column(Integer)
    status = Column(Enum(InvestimentoStatus), default=InvestimentoStatus.em_andamento)
    data = Column(Date, default=date.today())
    tipo_investimento = relationship("TipoInvestimento")
    rendimento_ids = relationship("Rendimento")
