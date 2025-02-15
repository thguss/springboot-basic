package org.programmers.weekly.mission.domain.voucher.model;

import java.io.Serializable;
import java.util.UUID;

// Serializable 왜 붙였나
public class FixedAmountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (beforeDiscount < amount) {
            return 0;
        }
        return beforeDiscount - amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
