package com.marparedes.citieslistapp.common

import com.marparedes.citieslistapp.data.model.CityDTO
import com.marparedes.citieslistapp.domain.model.City

fun CityDTO.toCity(): City {
    return City(id = this.id, name = this.name, country = this.country, coord = this.coord)
}