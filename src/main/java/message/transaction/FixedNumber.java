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

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * This class wraps valid CPX amounts
 * @author Artem Eger
 * @since 26.08.2019
 */
@Getter
@Setter
public class FixedNumber implements ISerialize {

    public final static long ZERO_VALUE = 0L;
    public final static long MIN_VALUE = 1L;
    public final static long ONE_VALUE = 1000000000000000000L;
    public final static BigDecimal MUL = BigDecimal.valueOf(1000L);

    public final static BigDecimal P = BigDecimal.valueOf(MIN_VALUE);
    public final static BigDecimal KP  = P.multiply(MUL);
    public final static BigDecimal MP = KP.multiply(MUL);
    public final static BigDecimal GP = MP.multiply(MUL);
    public final static BigDecimal KGP = GP.multiply(MUL);
    public final static BigDecimal MGP = KGP.multiply(MUL);
    public final static BigDecimal CPX = BigDecimal.valueOf(ONE_VALUE);

    private BigDecimal value;

    public FixedNumber(final double value, final BigDecimal mul){
        this.value = BigDecimal.valueOf(value).multiply(mul);
    }

    public byte [] getBytes() throws IOException {
        try(ByteArrayOutputStream out  = new ByteArrayOutputStream()) {
            try (DataOutputStream dataOut = new DataOutputStream(out)) {
                dataOut.write(this.value.toBigInteger().toByteArray().length);
                dataOut.write(this.value.toBigInteger().toByteArray());
                return out.toByteArray();
            }
        }
    }

}
