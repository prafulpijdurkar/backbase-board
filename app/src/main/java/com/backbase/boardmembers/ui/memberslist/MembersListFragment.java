package com.backbase.boardmembers.ui.memberslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backbase.boardmembers.R;
import com.backbase.boardmembers.interactors.BoardMembersInteractor;
import com.backbase.boardmembers.interactors.BoardMembersInteractorImpl;
import com.backbase.boardmembers.models.MembersResponseDTO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 17/02/17.
 */
public class MembersListFragment extends Fragment implements MembersListView{

    @Bind(R.id.layout_loading)
    View mLoadingLayout ;

    View rootView ;

    @Bind(R.id.members_recycle_view)
    RecyclerView membersRecycleView ;

    MembersListPresenter membersListPresenter ;

    MembersAdapter membersAdapter ;

    ButterKnife binder ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView  = inflater.inflate(R.layout.members_list_fragment, container , false);
        binder.bind(this,rootView);
        membersListPresenter = new MembersListPresenterImpl(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        membersListPresenter.getBoardMembersList();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        membersRecycleView.setLayoutManager(llm);
        membersRecycleView.setHasFixedSize(true);
    }

    @Override
    public void showLoading() {

        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {

        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {

        Snackbar.make(getView(),errorMessage,Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(membersListPresenter!=null)
                membersListPresenter.getBoardMembersList();
            }
        }).show();
    }

    @Override
    public void setBoardMembersList(List<MembersResponseDTO.MemberDetails> membersList) {

        membersAdapter = new MembersAdapter(getContext(),membersList, new MembersAdapter.OnMemberClickListener() {
            @Override
            public void onMemberClick(MembersResponseDTO.MemberDetails memberDetails) {

            }
        });
        membersRecycleView.setAdapter(membersAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        membersListPresenter.onDestroy();
        binder.unbind(this);
    }
}
