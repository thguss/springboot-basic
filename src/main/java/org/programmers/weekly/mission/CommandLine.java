package org.programmers.weekly.mission;

import org.programmers.weekly.mission.domain.customer.model.Customer;
import org.programmers.weekly.mission.domain.customer.service.CustomerService;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.service.VoucherService;
import org.programmers.weekly.mission.domain.wallet.WalletService;
import org.programmers.weekly.mission.util.io.Input;
import org.programmers.weekly.mission.util.io.Output;
import org.programmers.weekly.mission.util.type.Message;
import org.programmers.weekly.mission.util.type.OptionType;
import org.programmers.weekly.mission.util.type.VoucherType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandLine implements Runnable {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final Input input;
    private final Output output;

    private boolean isRunnable = true;

    @Autowired
    public CommandLine(VoucherService voucherService, CustomerService customerService, WalletService walletService, Input input, Output output) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            while (isRunnable) {
                switch (selectOptionType()) {
                    case CREATE -> createVoucher();
                    case LIST -> getListVoucher();
                    case EXIT -> exitVoucherProgram();
                    case WALLET -> selectWallet();
                    case BLACK_LIST -> getBlackList();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }

    private void selectWallet() {
        OptionType walletType = selectWalletType();

        switch (walletType) {
            case CREATE -> createWallet();
            case VOUCHER -> getVouchers();
            case DELETE -> deleteVoucher();
            case CUSTOMER -> getCustomers();
        }
    }

    private void deleteVoucher() {
        output.printMessage(Message.INPUT_CUSTOMER_ID.getMessage());
        UUID customerId = UUID.fromString(input.getInput());

        walletService.deleteVoucherCustomerHave(customerId);
    }

    private void getCustomers() {
        output.printMessage(Message.INPUT_CUSTOMER_VOUCHER_MESSAGE.getMessage());
        UUID voucherId = UUID.fromString(input.getInput());

        output.printMessage(Message.CUSTOMER_LIST.getMessage());
        List<Customer> customers = walletService.getCustomerByVoucherId(voucherId);

        customers.forEach((output::printObject));
    }

    private void getVouchers() {
        output.printMessage(Message.INPUT_CUSTOMER_VOUCHER_MESSAGE.getMessage());
        UUID customerId = UUID.fromString(input.getInput());

        output.printMessage(Message.VOUCHER_LIST.getMessage());
        List<Voucher> vouchers = walletService.getVoucherByCustomerId(customerId);

        vouchers.forEach((output::printObject));
    }

    private void createWallet() {
        output.printMessage(Message.INPUT_CUSTOMER_VOUCHER_MESSAGE.getMessage());
        String[] strings = input.getInput().split(" ");
        walletService.createWallet(UUID.fromString(strings[0]), UUID.fromString(strings[1]));
    }

    private OptionType selectWalletType() {
        output.printMessage(Message.SELECT_WALLET_MESSAGE.getMessage());
        Optional<OptionType> walletType = OptionType.checkType(input.getInput());

        if (walletType.isEmpty()) {
            throw new RuntimeException(Message.INPUT_ERROR_MESSAGE.getMessage());
        }

        return walletType.get();
    }

    private OptionType selectOptionType() {
        output.printMessage(Message.SELECT_OPTION_MESSAGE.getMessage());
        Optional<OptionType> optionType = OptionType.checkType(input.getInput());

        if (optionType.isEmpty()) {
            throw new RuntimeException(Message.INPUT_ERROR_MESSAGE.getMessage());
        }

        return optionType.get();
    }

    private VoucherType selectVoucherType() {
        output.printMessage(Message.SELECT_VOUCHER_MESSAGE.getMessage());
        Optional<VoucherType> voucherType = VoucherType.checkVoucherType(input.getInput());

        if (voucherType.isEmpty()) {
            throw new RuntimeException(Message.INPUT_ERROR_MESSAGE.getMessage());
        }

        return voucherType.get();
    }

    private Long selectVoucherDiscount() {
        output.printMessage(Message.SELECT_VOUCHER_INFO.getMessage());
        return input.getVoucherDiscount(input.getInput());
    }

    private void createVoucher() {
        VoucherType voucherType = selectVoucherType();
        Long discount = selectVoucherDiscount();
        voucherService.createVoucher(voucherType, discount);
    }

    private void getListVoucher() {
        output.printMessage(Message.VOUCHER_LIST.getMessage());
        voucherService.getVoucherList().forEach(output::printObject);
    }

    private void exitVoucherProgram() {
        isRunnable = false;
    }

    private void getBlackList() {
        output.printMessage(Message.BLACK_LIST.getMessage());
        customerService.getBlackList().forEach(output::printObject);
    }

}
