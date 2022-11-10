package org.programmers.kdtspringdemo.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);
}