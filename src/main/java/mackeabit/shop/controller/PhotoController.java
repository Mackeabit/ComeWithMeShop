package mackeabit.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mackeabit.shop.mapper.PhotoMapper;
import mackeabit.shop.service.OrderService;
import mackeabit.shop.service.ProductService;
import mackeabit.shop.service.ReviewService;
import mackeabit.shop.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final ProductService productService;

    @Value("${file.upload-dir1}")
    private String uploadDir1;

    @Value("${file.upload-dir2}")
    private String uploadDir2;

    private final PhotoMapper photoMapper;

    @PostMapping(value = "/reviewPhotos", consumes = { "multipart/form-data" })
    public String uploadPhoto(
            @RequestParam("photo") MultipartFile file,
            @RequestParam("member_idx") Long memberIdx,
            @RequestParam("order_idx") Long order_idx,
            ReviewsVO reviewsVO,
            String uploadingCheck) {

        String data = "N";

        log.info("file = {}",file);
        log.info("member_idx = {}",memberIdx);
        log.info("order_idx = {}",order_idx);
        log.info("ReviewsVO = {}",reviewsVO);
        log.info("uploadingCheck = {}",uploadingCheck);



        //리뷰 등록
        Long review_idx = reviewService.insertReview(reviewsVO);


        log.info("review_idx = {}", review_idx);


        if (review_idx == null) {
            return data;
        }


        // 파일 이름 생성
        String originalFilename = file.getOriginalFilename();


        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "." + extension;
        String fileSaveDbName = "img/user-img/" + fileName; // DB에 저장할 파일 경로 이름(file_name)

        // 파일 저장 경로 생성
        Path filePath = Paths.get(uploadDir1+ fileName);

        try {
            // 파일 저장 경로에 해당하는 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            // 파일 저장
            Files.write(filePath, file.getBytes());

            // Photo 객체 생성
            PhotosVO photo = new PhotosVO();
            photo.setMember_idx(memberIdx);
            photo.setImg_name(originalFilename);
            photo.setFile_name(fileSaveDbName);
//            photo.setNotice_idx(noticeIdx);
            photo.setReview_idx(review_idx);

            log.info("photosVO = {}", photo);

            /* uploadingCheck == 0 업로딩 파일 있음
             *  uploadingCheck == -1 업로딩 파일 없음 */
            if (uploadingCheck.equals("0")) {
                // 업로딩 파일이 있으면 Photo 객체 DB에 저장
                photoMapper.saveReviewPhoto(photo);
            }

            //모두 정상처리 되었다면 order_idx 를 통해 리뷰 체크 해주기
            int res = orderService.reviewCheck(order_idx);

            if (res > 0) {
                //상품 별점 평균 내기
                productService.updateProductStars(reviewsVO.getPd_nm());
            }

            // 성공적으로 저장되었음을 응답으로 반환
            data = "Y";

            return data;
        } catch (IOException e) {
            // 파일 저장 실패
            return data;
        }
    }

    @PostMapping(value = "/productImages", consumes = { "multipart/form-data" })
    public String productImages(@RequestParam("files") MultipartFile[] files, ProductsVO productsVO) {

        log.info("Start productImages Controller");
        String data = "N";

/*
        log.info("{}",productsVO.getPd_cnt());
        log.info("{}",productsVO.getOri_price());
        log.info("{}",productsVO.getPd_kind());
        log.info("{}",productsVO.getPd_size());
        log.info("{}",productsVO.getPd_color());
        log.info("{}",productsVO.getPd_contents());
        log.info("{}",productsVO.getCategory_code());
        log.info("{}",productsVO.getSell_price());
*/


        // 상품 등록
        Long pd_idx = productService.insertProduct(productsVO);

        log.info("Insert pd_idx = {}", pd_idx);

        // 상품 로그 테이블 등록
        Products_logVO products_logVO = new Products_logVO();
        products_logVO.setPd_idx(pd_idx);
        productService.insertProductLogByPd_idx(products_logVO);

        // 상품 별점 셋팅 (기본값 셋팅)
        Products_starsVO products_starsVO = new Products_starsVO();
        products_starsVO.setPd_nm(productsVO.getPd_nm());
        productService.insertProductStar(products_starsVO);

        log.info("product = {}", productsVO);


        // 상품 이미지를 저장할 객체 생성
        Product_imgVO product_imgVO = new Product_imgVO();
        product_imgVO.setPd_idx(pd_idx);

        int checkLoop = 0;

        for (MultipartFile file : files) {

            // 파일 이름 생성
            String originalFilename = file.getOriginalFilename();

            if (checkLoop == 0) {
                product_imgVO.setPd_img(originalFilename);
            } else if (checkLoop == 1) {
                product_imgVO.setPd_imgCt(originalFilename);
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "." + extension;
            String fileSaveDbName = "img/product-img/" + fileName; // DB에 저장할 파일 경로 이름(file_name)

            // 파일 저장 경로 생성
            Path filePath = Paths.get(uploadDir2+ fileName);

            try {
                // 파일 저장 경로에 해당하는 디렉토리가 존재하지 않으면 생성
                if (!Files.exists(filePath.getParent())) {
                    Files.createDirectories(filePath.getParent());
                }

                // 파일 저장
                Files.write(filePath, file.getBytes());

                // DB 에 저장할 이미지 경로 세팅
                if (checkLoop == 0) {
                    product_imgVO.setSv_loc(fileSaveDbName);
                } else if (checkLoop == 1) {
                    product_imgVO.setSv_locCt(fileSaveDbName);
                }


                checkLoop++;

            } catch (IOException e) {
                // 파일 저장 실패
                log.info("저장 실패 = {}", e);
            }

        }//for문

        log.info("checkLoop = {}",checkLoop);
        log.info("setting product_img = {}", product_imgVO);

        //Product_imgVO 세팅 끝난 후 DB 저장
        productService.insertProductImg(product_imgVO);


        return data;
    }






}
