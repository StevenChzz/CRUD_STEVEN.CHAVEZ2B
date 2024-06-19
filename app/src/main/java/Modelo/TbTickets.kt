package Modelo

data class TbTickets(
    val uuid: String,
    var titulo: String,
    var descripcion: String,
    var autor: String,
    var emailContacto: String,
    var fechaCreacion: String,
    var estado: String,
    var fechaFinalizacion: String

)
