/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solana.mobilewalletadapter.walletlib.scenario;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.VisibleForTesting;

import com.solana.mobilewalletadapter.walletlib.protocol.MobileWalletAdapterServer;

public abstract class SignPayloadRequest extends ScenarioRequest {
    @NonNull
    protected final MobileWalletAdapterServer.SignPayloadRequest mRequest;

    @NonNull
    protected final String mPublicKey;

    protected SignPayloadRequest(@NonNull MobileWalletAdapterServer.SignPayloadRequest request,
                                 @NonNull String publicKey) {
        super(request);
        mRequest = request;
        mPublicKey = publicKey;
    }

    @NonNull
    public String getPublicKey() {
        return mPublicKey;
    }

    @NonNull
    @Size(min = 1)
    public byte[][] getPayloads() {
        return mRequest.payloads;
    }

    public void completeWithSignedPayloads(@NonNull @Size(min = 1) byte[][] signedPayloads) {
        mRequest.complete(new MobileWalletAdapterServer.SignedPayloadResult(signedPayloads));
    }

    public void completeWithDecline() {
        mRequest.completeExceptionally(new MobileWalletAdapterServer.RequestDeclinedException(
                "sign request declined"));
    }

    public void completeWithInvalidPayloads(@NonNull @Size(min = 1) boolean[] valid) {
        mRequest.completeExceptionally(new MobileWalletAdapterServer.InvalidPayloadException(
                "One or more invalid payloads provided", valid));
    }

    public void completeWithTooManyPayloads() {
        mRequest.completeExceptionally(new MobileWalletAdapterServer.TooManyPayloadsException(
                "Number of payloads provided for signing exceeds implementation limit"));
    }

    @VisibleForTesting
    public void completeWithReauthorizationRequired() {
        mRequest.completeExceptionally(new MobileWalletAdapterServer.ReauthorizationRequiredException(
                "auth_token requires reauthorization"));
    }

    @VisibleForTesting
    public void completeWithAuthTokenNotValid() {
        mRequest.completeExceptionally(new MobileWalletAdapterServer.AuthTokenNotValidException(
                "auth_token not valid for signing of this payload"));
    }
}
