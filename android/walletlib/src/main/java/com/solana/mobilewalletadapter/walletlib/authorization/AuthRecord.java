/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solana.mobilewalletadapter.walletlib.authorization;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import java.util.Objects;

public class AuthRecord {
    @IntRange(from = 1)
    /*package*/ final int id;

    @NonNull
    public final IdentityRecord identity;

    @IntRange(from = 0)
    public final long issued;

    @IntRange(from = 0)
    public final long expires;

    @NonNull
    public final String publicKey;

    /*package*/ final int publicKeyId;

    private boolean mRevoked;

    /*package*/ AuthRecord(@IntRange(from = 1) int id,
                           @NonNull IdentityRecord identity,
                           @NonNull String publicKey,
                           @IntRange(from = 1) int publicKeyId,
                           @IntRange(from = 0) long issued,
                           @IntRange(from = 0) long expires) {
        // N.B. This is a package-visibility constructor; these values will all be validated by
        // other components within this package.
        this.id = id;
        this.identity = identity;
        this.publicKey = publicKey;
        this.publicKeyId = publicKeyId;
        this.issued = issued;
        this.expires = expires;
    }

    public boolean isExpired() {
        final long now = System.currentTimeMillis();
        return (now < issued || now > expires);
    }

    public boolean isAuthorized() {
        synchronized (this) {
            return !mRevoked;
        }
    }

    /*package*/ void setRevoked() {
        synchronized (this) {
            mRevoked = true;
        }
    }

    public boolean isRevoked() {
        synchronized (this) {
            return mRevoked;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRecord that = (AuthRecord) o;
        return id == that.id; // equality is strictly defined by the database primary key
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // id is the database primary key
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthRecord{" +
                "id=" + id +
                ", identity=" + identity +
                ", publicKey='" + publicKey + '\'' +
                ", issued=" + issued +
                ", expires=" + expires +
                ", mRevoked=" + mRevoked +
                '}';
    }
}
