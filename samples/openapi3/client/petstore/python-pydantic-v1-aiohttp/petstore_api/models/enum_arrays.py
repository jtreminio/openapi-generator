# coding: utf-8

"""
    OpenAPI Petstore

    This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\

    The version of the OpenAPI document: 1.0.0
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


from __future__ import annotations
import pprint
import re  # noqa: F401
import json


from typing import List, Optional
from pydantic import BaseModel, StrictStr, conlist, validator

class EnumArrays(BaseModel):
    """
    EnumArrays
    """
    just_symbol: Optional[StrictStr] = None
    array_enum: Optional[conlist(StrictStr)] = None
    __properties = ["just_symbol", "array_enum"]

    @validator('just_symbol')
    def just_symbol_validate_enum(cls, value):
        """Validates the enum"""
        if value is None:
            return value

        if value not in ('>=', '$',):
            raise ValueError("must be one of enum values ('>=', '$')")
        return value

    @validator('array_enum')
    def array_enum_validate_enum(cls, value):
        """Validates the enum"""
        if value is None:
            return value

        for i in value:
            if i not in ('fish', 'crab',):
                raise ValueError("each list item must be one of ('fish', 'crab')")
        return value

    class Config:
        """Pydantic configuration"""
        allow_population_by_field_name = True
        validate_assignment = True

    def to_str(self) -> str:
        """Returns the string representation of the model using alias"""
        return pprint.pformat(self.dict(by_alias=True))

    def to_json(self) -> str:
        """Returns the JSON representation of the model using alias"""
        return json.dumps(self.to_dict())

    @classmethod
    def from_json(cls, json_str: str) -> EnumArrays:
        """Create an instance of EnumArrays from a JSON string"""
        return cls.from_dict(json.loads(json_str))

    def to_dict(self):
        """Returns the dictionary representation of the model using alias"""
        _dict = self.dict(by_alias=True,
                          exclude={
                          },
                          exclude_none=True)
        return _dict

    @classmethod
    def from_dict(cls, obj: dict) -> EnumArrays:
        """Create an instance of EnumArrays from a dict"""
        if obj is None:
            return None

        if not isinstance(obj, dict):
            return EnumArrays.parse_obj(obj)

        _obj = EnumArrays.parse_obj({
            "just_symbol": obj.get("just_symbol"),
            "array_enum": obj.get("array_enum")
        })
        return _obj


