package com.java.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.java.dto.Poll;
import com.java.dto.Poll_Item;
import com.java.dto.Vote_Log;
import com.java.entity.Member;

public interface PollService {
    public Poll savePoll(Poll poll, List<Poll_Item> pollItems);
    
    Page<Poll> getPollList(Pageable pageable);

    Page<Poll> getActivePollList(Pageable pageable);
    
    long getVoteCountByPollNo(int pollNo);
    
    void saveVoteLog(Vote_Log voteLog);
    
    boolean hasVoted(int pollNo, String memberId);

    void vote(int pollNo, int itemNo, String memberId);
    
    void deletePoll(int pollNo);

    Page<Poll> searchByTitle(String keyword, Pageable pageable);
    Page<Poll> searchByContent(String keyword, Pageable pageable);

	public void savePollAndItems(String poll_title, String poll_content, String poll_end_date, List<String> poll_items,
			MultipartFile file, Member loggedInMember) throws Exception;
}