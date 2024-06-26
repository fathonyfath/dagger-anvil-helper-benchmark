package dev.fathony.anvilhelper.anvil

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.fathony.anvilhelper.anvil.databinding.ItemGroupBinding
import dev.fathony.anvilhelper.anvil.databinding.ItemPageBinding

class MainAdapter(
    private val context: Context,
    private val activityLauncher: ActivityLauncher,
    private val items: List<MainItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MainItem.Header -> MainItem.Header::class.hashCode()
            is MainItem.Item -> MainItem.Item::class.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MainItem.Header::class.hashCode() -> HeaderViewHolder(
                ItemGroupBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            MainItem.Item::class.hashCode() -> PageViewHolder(
                ItemPageBinding.inflate(
                    layoutInflater,
                    parent, false
                )
            )

            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HeaderViewHolder -> (item as? MainItem.Header)?.let { holder.bind(it) }
            is PageViewHolder -> (item as? MainItem.Item)?.let { data ->
                holder.bind(data) {
                    activityLauncher.launchActivity(data.page.intentBuilder.create(context))
                }
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MainItem.Header) {
            binding.root.text = data.group.name
        }
    }

    class PageViewHolder(private val binding: ItemPageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MainItem.Item, onClick: () -> Unit) {
            binding.root.text = data.page.name
            binding.root.setOnClickListener { onClick.invoke() }
        }
    }
}
