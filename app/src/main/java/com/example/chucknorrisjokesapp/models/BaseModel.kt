package com.example.chucknorrisjokesapp.models

data class BaseModel(
    val icon : String,
    val id : String,
    val value : String
)
{
    companion object {

        fun map(bs : BaseModelDTO) : BaseModel
        {
            return BaseModel(
                bs.icon_url,
                bs.id,
                bs.value
            )
        }
    }
}


