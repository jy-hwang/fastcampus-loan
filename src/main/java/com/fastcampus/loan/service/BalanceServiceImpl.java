package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Balance;
import com.fastcampus.loan.dto.BalanceDTO.CreateRequest;
import com.fastcampus.loan.dto.BalanceDTO.RepaymentRequest;
import com.fastcampus.loan.dto.BalanceDTO.RepaymentRequest.RepaymentType;
import com.fastcampus.loan.dto.BalanceDTO.Response;
import com.fastcampus.loan.dto.BalanceDTO.UpdateRequest;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, CreateRequest createRequest) {
        // 데이터가 있는지 여부 체크
        if (balanceRepository.findByApplicationId(applicationId).isPresent()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Balance balance = modelMapper.map(createRequest, Balance.class);

        BigDecimal entryAmount = createRequest.getEntryAmount();
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        // 대출집행이 삭제되는 경우
        balanceRepository.findByApplicationId(applicationId).ifPresent(b -> {
            balance.setBalanceId(b.getBalanceId());
            balance.setIsDeleted(b.getIsDeleted());
            balance.setCreatedAt(b.getCreatedAt());
            balance.setUpdatedAt(b.getUpdatedAt());
        });

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Balance balance = balanceRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(balance, Response.class);
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {
        // balance
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });


        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getAfterEntryAmount();
        BigDecimal updatedBalance = balance.getBalance();

        // as-is -> to-be
        // balance - +
        updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updatedBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, Response.class);
    }

    @Override
    public Response repaymentUpdate(Long applicationId, RepaymentRequest request) {
        // balance
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal updatedBalance = balance.getBalance();
        BigDecimal repaymentAmount = request.getRepaymentAmount();

        if (request.getType().equals(RepaymentType.ADD)) {
            updatedBalance = updatedBalance.add(repaymentAmount);
        } else {
            updatedBalance = updatedBalance.subtract(repaymentAmount);
        }

        balance.setBalance(updatedBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        // balance
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        balance.setIsDeleted(true);

        balanceRepository.save(balance);
    }
}
