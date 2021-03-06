/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 - 2019
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package message.transaction;

import crypto.CPXKey;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;

@AllArgsConstructor
public class TransactionFactory implements IProduceTransaction {

    @Override
    public Transaction create(final TxObj objType, final ECPrivateKey privateKey,
                              final ISerialize payload, final String target,
                              final long nonce, final FixedNumber amount,
                              final FixedNumber gasPrice, final FixedNumber gasLimit) throws IOException {

        byte txType;
        switch (objType){
            case TRANSFER:
                txType = TransactionType.TRANSFER;
                break;
            case DEPLOY:
                txType = TransactionType.DEPLOY;
                break;
            case REFUND:
                txType = TransactionType.REFUND;
                break;
            case SCHEDULE:
                txType = TransactionType.SCHEDULE;
                break;
            default:
                txType = TransactionType.CALL;
        }

        return Transaction.builder()
                .txType(txType)
                .fromPubKeyHash(CPXKey.getScriptHash(privateKey))
                .toPubKeyHash(target)
                .amount(amount)
                .nonce(nonce)
                .data(payload.getBytes())
                .gasPrice(gasPrice)
                .gasLimit(gasLimit.getValue())
                .version(1)
                .executeTime(Instant.now().toEpochMilli())
                .build();
    }

}
