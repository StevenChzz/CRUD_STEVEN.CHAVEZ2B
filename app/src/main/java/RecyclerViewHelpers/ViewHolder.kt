package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stevench.example.crudstevenchavez.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var txtNombreCard = view.findViewById<TextView>(R.id.txtTitulo)
    val txtDescripcionCard = view.findViewById<TextView>(R.id.txtDescripcionCard)
    val txtAutor = view.findViewById<TextView>(R.id.txtAutor)
    val txtEmailContacto = view.findViewById<TextView>(R.id.txtEmailContacto)
    val txtEstadoCard = view.findViewById<TextView>(R.id.txtEstadoCard)
    val txtFechaCreacionCard = view.findViewById<TextView>(R.id.txtFechaCreacionCard)
    val txtFechaFinalizacionCard = view.findViewById<TextView>(R.id.txtFechaFinalizacionCard)

    val btnEditar: ImageView = view.findViewById(R.id.imgEditar)
    val btnEliminar: ImageView = view.findViewById(R.id.imgEliminar)

}