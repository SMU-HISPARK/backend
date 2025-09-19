package com.java.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.java.dto.Poll;
import com.java.dto.Poll_Item;
import com.java.dto.Vote_Log;
import com.java.entity.Member;
import com.java.repository.PollRepository;
import com.java.repository.Poll_ItemRepository;
import com.java.repository.Vote_LogRepository;
import com.java.repository.MemberRepository; 

@Service
public class PollServiceImpl implements PollService {
    
    @Autowired
    private PollRepository pollRepository;
    
    @Autowired
    private Poll_ItemRepository pollItemRepository;
    
    @Autowired
    private Vote_LogRepository voteLogRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Override
    @Transactional
    public Poll savePoll(Poll poll, List<Poll_Item> pollItems) {
        try {
            Poll savedPoll = pollRepository.save(poll);
            
            if (pollItems != null && !pollItems.isEmpty()) {
                for (Poll_Item pollItem : pollItems) {
                    pollItem.setPoll(savedPoll);
                    pollItemRepository.save(pollItem);
                }
            }
            return savedPoll;
        } catch (Exception e) {
            // 예외 처리
            return null;
        }
    }
    
    @Override
    public Page<Poll> getPollList(Pageable pageable) {
        return pollRepository.findAllByOrderByPoll_noDesc(pageable);
    }

    @Override
    public Page<Poll> getActivePollList(Pageable pageable) {
        return pollRepository.findAllActivePolls(new Timestamp(System.currentTimeMillis()), pageable);
    }
    
    @Override
    public long getVoteCountByPollNo(int pollNo) {
        return voteLogRepository.countByPollNo(pollNo);
    }
    
    @Override
    public void saveVoteLog(Vote_Log voteLog) {
        voteLogRepository.save(voteLog);
    }
    
    @Override
    public boolean hasVoted(int pollNo, String memberId) {
        return voteLogRepository.findByPoll_PollNoAndMember_LoginId(pollNo, memberId).isPresent();
    }
    
    @Override
    @Transactional
    public void vote(int pollNo, int itemNo, String loginId) {
        Poll poll = pollRepository.findById(pollNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid poll No:" + pollNo));
        
        Poll_Item pollItem = pollItemRepository.findById(itemNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid poll item No:" + itemNo));
        
        // Member 객체가 존재하는지 확인 (가장 가능성이 높은 오류 지점)
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
        		() -> new IllegalArgumentException("해당 loginId를 가진 회원이 없습니다."));
        if (member == null) {
            throw new IllegalArgumentException("Invalid member ID: " + loginId);
        }
        
        // 이미 투표했는지 확인하는 로직 추가
        if (hasVoted(pollNo, loginId)) {
            throw new IllegalStateException("Member has already voted for this poll.");
        }

        // 투표 로그 저장
        Vote_Log voteLog = new Vote_Log();
        voteLog.setPoll(poll);
        voteLog.setPollItem(pollItem);
        voteLog.setMember(member);
        
        voteLogRepository.save(voteLog);
    }
    
    @Override
    @Transactional
    public void deletePoll(int pollNo) {
        // 1. 투표 기록(Vote_Log) 삭제
        voteLogRepository.deleteByPoll_PollNo(pollNo);

        // 2. 투표 항목(Poll_Item) 삭제
        pollItemRepository.deleteByPoll_PollNo(pollNo);

        // 3. 투표(Poll) 자체 삭제
        pollRepository.deleteById(pollNo);
    }
    
    @Override
    public Page<Poll> searchByTitle(String keyword, Pageable pageable) {
        // PollRepository의 @Query가 적용된 메소드 호출
        return pollRepository.searchByTitle(keyword, pageable);
    }

    @Override
    public Page<Poll> searchByContent(String keyword, Pageable pageable) {
        // PollRepository의 @Query가 적용된 메소드 호출
        return pollRepository.searchByContent(keyword, pageable);
    }

    // pollService에 투표수 계산하는 메소드 추가
    public long getVoteCountByItemNo(int itemNo) {
        return voteLogRepository.countByPollItemNo(itemNo); // 메서드 이름 수정
    }

    @Override
    @Transactional
    public void savePollAndItems(String poll_title, String poll_content, String poll_end_date, List<String> poll_items,
                                 MultipartFile file, Member loggedInMember) throws Exception {

        Poll poll = new Poll();
        poll.setPoll_title(poll_title);
        poll.setPoll_content(poll_content);
        poll.setMember(loggedInMember);
        
        // poll_start_date는 DTO에 설정된 @CreationTimestamp에 의해 자동으로 생성됩니다.
        
        // poll_end_date 문자열을 Timestamp로 변환하여 저장
        if (poll_end_date != null && !poll_end_date.isEmpty()) {
            try {
                Timestamp endDate = Timestamp.valueOf(poll_end_date.replace("T", " ") + ":00");
                poll.setPoll_end_date(endDate);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid date format: " + poll_end_date);
            }
        }

        // 파일 업로드 처리
        if (file != null && !file.isEmpty()) {
            String originFileName = file.getOriginalFilename();
            long time = System.currentTimeMillis();
            String uploadFileName = String.format("%d_%s", time, originFileName);

            String fileUrl = "C:/uploads/";
            File f = new File(fileUrl + uploadFileName);

            file.transferTo(f);
            poll.setPoll_file(uploadFileName);
        }

        // Poll 먼저 저장하여 poll_no를 생성
        Poll savedPoll = pollRepository.save(poll);

        // Poll_Item 저장: 빈 항목은 저장하지 않도록 검증 로직 추가
        if (poll_items != null && !poll_items.isEmpty()) {
            for (String itemContent : poll_items) {
                if (itemContent != null && !itemContent.trim().isEmpty()) {
                    Poll_Item pollItem = new Poll_Item();
                    pollItem.setItem_content(itemContent);
                    pollItem.setPoll(savedPoll); // FK인 poll_no를 설정
                    pollItemRepository.save(pollItem);
                }
            }
        }
        
        
        
    }
    
	
}