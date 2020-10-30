package com.sns.server.job.postcreate;

import com.sns.server.account.Account;
import com.sns.server.account.AccountRepository;
import com.sns.server.hashtag.HashTag;
import com.sns.server.hashtag.HashTagRepository;
import com.sns.server.image.Image;
import com.sns.server.image.ImageRepository;
import com.sns.server.post.Post;
import com.sns.server.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostsCreateTasklet implements Tasklet {

    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;
    private final ImageRepository imageRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Account> accounts = accountRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#셀카 #셀카그램 #셀스타 #얼스타그램 #셀피 #셀피그램 #셀기꾼 #거울샷 #셀 #거울셀카 #전신샷 #기본카메라 #재탕 #셀고 #얼스타 #오늘의흔녀");
        stringBuilder.append(" #오늘의흔남 #무쌍 #selsta #selstagram #얼스타그램셀카 #셀카놀이 #얼카 #셀카타임 #셀피꾼 #셀끼꾼 #셀피족 #셀카잼 #셀카샷");
        stringBuilder.append(" #패션 #데일리룩 #데일리룩코디 #데일리룩그램 #멋스타그램 #오오티디 #ootd #daily #패션스타그램 #일상룩 #일상룩코디 #패션그램");
        stringBuilder.append(" #아웃핏 #패피 #패피녀 #패피남 #신상룩 #전신샷 #미러샷 #거울샷 #옷스타그램 #코디 #패션피플 #스타일 #오오티디룩 #dailylook");
        stringBuilder.append(" #여행 #어디까지가봤니 #여행에미치다 #떠나고싶다 #여행스타그램 #감성여행 #여행사진 #여행후기 #여행중 #감성여행 #여행이좋다");
        stringBuilder.append(" #여행기록 #여행일기 #여행중독 #여행앓이 #travel #비행스타그램 #추억스타그램 #추억 #떠나자 #(여행지역) #놀러가자 #휴가스타그램 #trip");


        String[] tags = stringBuilder.toString().split(" ");
        List<HashTag> hashTags = Arrays.stream(tags)
                                       .map(tag -> HashTag.builder()
                                                          .tag(tag)
                                                          .build())
                                       .collect(Collectors.toList());

        List<HashTag> savedHashTags = hashTagRepository.saveAll(hashTags);

        List<String> images = new ArrayList<>();
        images.add("https://cdn.pixabay.com/photo/2018/07/14/15/27/cafe-3537801_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2013/02/21/19/06/beach-84533_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2017/08/06/12/06/people-2591874_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2017/06/05/11/01/airport-2373727_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2017/12/15/13/51/polynesia-3021072_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2016/01/09/18/27/old-1130731_1280.jpg");
        images.add("https://cdn.pixabay.com/photo/2015/10/30/20/13/sunrise-1014712_1280.jpg");

        for (int i = 0; i < 50; i++) {
            List<HashTag> postHashTags = new ArrayList<>();

            int hashTagSize = savedHashTags.size();
            int randomSize = new Random().nextInt(30) + 20;
            for (int j = 0; j < hashTagSize / randomSize; j++) {
                if (j > 0) postHashTags.add(savedHashTags.get(j));
            }

            Post post = postRepository.save(Post.builder()
                                                .account(accounts.get(new Random().nextInt(accounts.size())))
                                                .hashTags(postHashTags)
                                                .title("test" + i)
                                                .content("test" + i)
                                                .build());

            imageRepository.saveAll(images.stream().map(image ->
                    Image.builder().url(image).post(post).build())
                                          .collect(Collectors.toList()));
        }


        return RepeatStatus.FINISHED;
    }
}
