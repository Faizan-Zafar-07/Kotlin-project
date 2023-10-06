import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.ModelUser
import com.example.kotlinproject.R

class AdapterUer(private val data: List<String>) : RecyclerView.Adapter<AdapterUer.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val ModelUser = data[position] // Assuming userData is a List or an array
        holder.item_name.text = "ModelUser.title"
        holder.item_date.text = "ModelUser.Date"

    }

    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item_name: TextView = itemView.findViewById(R.id.text_name)
        var item_date: TextView = itemView.findViewById(R.id.dueDate_month)


    }
}
