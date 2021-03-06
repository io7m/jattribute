/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */


package com.io7m.jattribute.core;

import com.io7m.jattribute.core.internal.Attribute;
import com.io7m.jattribute.core.internal.AttributeFunction;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A class for constructing attributes.
 */

public final class Attributes
{
  private final Consumer<Throwable> errorConsumer;

  /**
   * Construct a new attributes class.
   *
   * @param inErrorConsumer A function that will receive any exceptions raised
   *                        by subscribers of attributes.
   */

  private Attributes(
    final Consumer<Throwable> inErrorConsumer)
  {
    this.errorConsumer =
      Objects.requireNonNull(inErrorConsumer, "errorConsumer");
  }

  /**
   * Construct a new attributes class.
   *
   * @param inErrorConsumer A function that will receive any exceptions raised
   *                        by subscribers of attributes.
   *
   * @return A new attributes class
   */

  public static Attributes create(
    final Consumer<Throwable> inErrorConsumer)
  {
    return new Attributes(inErrorConsumer);
  }

  /**
   * Create a new attribute with the given initial value.
   *
   * @param initial The initial value
   * @param <A>     The type of attributes
   *
   * @return A new attribute
   */

  public <A> AttributeType<A> withValue(
    final A initial)
  {
    return new Attribute<>(this.errorConsumer, initial);
  }

  /**
   * Create a new attribute that evaluates the given function to retrieve
   * values.
   *
   * @param f   The evaluated function
   * @param <A> The type of attributes
   *
   * @return A new attribute
   */

  public <A> AttributeType<A> fromFunction(
    final Supplier<A> f)
  {
    return new AttributeFunction<>(this.errorConsumer, f);
  }
}
