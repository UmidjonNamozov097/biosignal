package com.example.biosignals.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.example.biosignals.R
import com.example.biosignals.database.MyDatabase
import com.example.biosignals.databinding.FragmentUserListBinding
import com.example.biosignals.databinding.ItemUserBinding
import com.example.biosignals.model.User
import com.example.biosignals.util.UserInputDialog
import com.example.biosignals.util.formatTime
import com.example.biosignals.viewmodel.UserListViewModel
import com.example.biosignals.viewmodel.factory.UserListViewModelFactory
import java.util.*


class UserListFragment : BaseFragment<FragmentUserListBinding>(FragmentUserListBinding::inflate) {

    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: ListDelegationAdapter<List<User>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = MyDatabase.getInstance(requireContext())
        viewModel = ViewModelProvider(this, UserListViewModelFactory(db.userDao))
            .get(UserListViewModel::class.java)
        intiListeners()
        initList()
    }

    private fun initList() {
        val b = Bundle()
        adapter = ListDelegationAdapter(userAdapterDelegate {
            b.putLong("USER_ID", it.id!!)
            findNavController().navigate(R.id.dataSetFragment, b)
        })
        adapter.items = viewModel.dataList.value
        if (!viewModel.dataList.value.isNullOrEmpty())
            binding.emptyListText.visibility = View.GONE
        binding.dataList.layoutManager = LinearLayoutManager(requireContext())
        binding.dataList.adapter = adapter
    }

    private fun intiListeners() {
        binding.fabUser.setOnClickListener {
            UserInputDialog(requireContext()) {
                viewModel.db.insert(User(null, it, Date().time))
            }
        }
        viewModel.dataList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.emptyListText.visibility = View.GONE
                adapter.items = it
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun userAdapterDelegate(itemClickedListener: (User) -> Unit) =
        adapterDelegateViewBinding<User, User, ItemUserBinding>(
            { layoutInflater, root -> ItemUserBinding.inflate(layoutInflater, root, false) }
        ) {
            binding.layoutCont.setOnClickListener {
                itemClickedListener(item)
            }
            bind {
                binding.userNameText.text = item.name
                binding.timeTxt.text = formatTime(item.createdTime)
            }
        }

}