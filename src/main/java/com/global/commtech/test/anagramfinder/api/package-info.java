/**
 * Publish/consumer API that promotes the idea of producers creating data that can be consumed downstream by consumers.
 * Transformers are a type of consumer that consume incoming data and passes on transformed data to the next consumer.
 * This allows for the separation of concerns of how data is generated, transformed and consumed.
 * <p>
 * Producer -> Transformer -> Transformer -> Consumer
 */
package com.global.commtech.test.anagramfinder.api;