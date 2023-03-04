package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.entity.UserRoleEnum;
import com.sparta.myselectshop.jwt.JwtUtil;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기 .saveAndFlush()는 바로 DB에다가 저장 쿼리를 날리는 메서드.
            Product product = productRepository.saveAndFlush(new Product(requestDto, user.getId()));

            return new ProductResponseDto(product);
        } else {
            return null;
        }
    }


    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProducts(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request); // 토큰을 가져온다
        Claims claims;      //JWT안에 들어있는 정보들을 담을 수 있는 객체

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if(token != null) {
            // Token 검증 .validateToken()은 검증이 잘되면 true
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져와서 claims에 넣음. 문제가 생기면 예외처리
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회 / claims.getSubject()를 하면 username 가져온다
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
            UserRoleEnum userRoleEnum = user.getRole();
            System.out.println("role = " + userRoleEnum);

            List<ProductResponseDto> list = new ArrayList<>();
            List<Product> productList;

            if (userRoleEnum == UserRoleEnum.USER) {
                // 사용자 권한이 USER일 경우 USER것만 가져오고 관리자면 다 가져옴
                productList = productRepository.findAllByUserId(user.getId());
            } else {
                productList = productRepository.findAll();
            }

//            그렇게 가져온 productlist를 DTO에 담아서 반환
            for (Product product : productList) {
                list.add(new ProductResponseDto(product));
            }

            return list;

        }else {
            return null;
        }
    }

    @Transactional
    public Long updateProduct(Long id, ProductMypriceRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // product id와 그 product가 동일한 userid까지 가지고있는지 확인
//            현재 로그인한 유저가 선택한 product가 맞는지 확인
            Product product = productRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
            );

            product.update(requestDto);

            return product.getId();

        } else {
            return null;
        }
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 상품은 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
    }
}