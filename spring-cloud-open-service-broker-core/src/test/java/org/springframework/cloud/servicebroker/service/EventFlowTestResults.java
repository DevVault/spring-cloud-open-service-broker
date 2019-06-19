/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.servicebroker.service;

import reactor.core.publisher.Mono;

class EventFlowTestResults {

	private String beforeCreate;

	private String afterCreate;

	private String errorCreate;

	private String beforeDelete;

	private String afterDelete;

	private String errorDelete;

	private String beforeUpdate;

	private String afterUpdate;

	private String errorUpdate;

	private String beforeLastOperation;

	private String afterLastOperation;

	private String errorLastOperation;

	String getBeforeCreate() {
		return beforeCreate;
	}

	Mono<Void> setBeforeCreate(String s) {
		return Mono.fromCallable(() -> this.beforeCreate = s)
				.then();
	}

	String getAfterCreate() {
		return afterCreate;
	}

	Mono<Void> setAfterCreate(String s) {
		return Mono.fromCallable(() -> this.afterCreate = s)
				.then();
	}

	String getErrorCreate() {
		return errorCreate;
	}

	Mono<Void> setErrorCreate(String s) {
		return Mono.fromCallable(() -> this.errorCreate = s)
				.then();
	}

	String getBeforeDelete() {
		return beforeDelete;
	}

	Mono<Void> setBeforeDelete(String s) {
		return Mono.fromCallable(() -> this.beforeDelete = s)
				.then();
	}

	String getAfterDelete() {
		return afterDelete;
	}

	Mono<Void> setAfterDelete(String s) {
		return Mono.fromCallable(() -> this.afterDelete = s)
				.then();
	}

	String getErrorDelete() {
		return errorDelete;
	}

	Mono<Void> setErrorDelete(String s) {
		return Mono.fromCallable(() -> this.errorDelete = s)
				.then();
	}

	String getBeforeUpdate() {
		return beforeUpdate;
	}

	Mono<Void> setBeforeUpdate(String s) {
		return Mono.fromCallable(() -> this.beforeUpdate = s)
				.then();
	}

	String getAfterUpdate() {
		return afterUpdate;
	}

	Mono<Void> setAfterUpdate(String s) {
		return Mono.fromCallable(() -> this.afterUpdate = s)
				.then();
	}

	String getErrorUpdate() {
		return errorUpdate;
	}

	Mono<Void> setErrorUpdate(String s) {
		return Mono.fromCallable(() -> this.errorUpdate = s)
				.then();
	}

	String getBeforeLastOperation() {
		return beforeLastOperation;
	}

	Mono<Void> setBeforeLastOperation(String s) {
		return Mono.fromCallable(() -> this.beforeLastOperation = s)
				.then();
	}

	String getAfterLastOperation() {
		return afterLastOperation;
	}

	Mono<Void> setAfterLastOperation(String s) {
		return Mono.fromCallable(() -> this.afterLastOperation = s)
				.then();
	}

	String getErrorLastOperation() {
		return errorLastOperation;
	}

	Mono<Void> setErrorLastOperation(String s) {
		return Mono.fromCallable(() -> this.errorLastOperation = s)
				.then();
	}
}
