package es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp

//Usaremos dicha clase como data class
data class User(val id: Int, var name: String, var surname: String, var url: String) {
    fun getFullName() : String = "$name $surname"
}