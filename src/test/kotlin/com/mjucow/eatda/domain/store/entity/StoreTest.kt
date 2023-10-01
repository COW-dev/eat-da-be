package com.mjucow.eatda.domain.store.entity

import com.mjucow.eatda.common.vo.DayOfWeek
import com.mjucow.eatda.domain.store.entity.objectmother.StoreHoursMother
import com.mjucow.eatda.domain.store.entity.objectmother.StoreMother
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.test.util.ReflectionTestUtils

class StoreTest {
    @DisplayName("이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNameIsEmpty(name: String) {
        // given

        // when
        val throwable = catchThrowable { Store(name = name, address = StoreMother.ADDRESS) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNameLengthGreaterThanMaxLength() {
        // given
        val name = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { Store(name = name, address = StoreMother.ADDRESS) }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenDisplayNameLengthGreaterThanMaxLength() {
        // given
        val name = "validName"
        val displayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable {
            Store(name = name, address = StoreMother.ADDRESS, displayName = displayName)
        }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewNameIsEmpty(newName: String) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable { store.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewNameLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.create()
        val newName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.name = newName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 보이는 이름이 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewDisplayNameLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.create()
        val newDisplayName = "x".repeat(Store.MAX_NAME_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.displayName = newDisplayName }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("보이는 이름이 없다면 이름이 보여진다")
    @Test
    fun returnNameWhenDisplayNameIsNull() {
        // given
        val store = StoreMother.create()

        // when

        // then
        assertThat(store.displayedName).isEqualTo(store.name)
    }

    @DisplayName("보이는 이름이 있다면 보이는 이름이 보여진다")
    @Test
    fun returnDisplayNameWhenDisplayNameIsNotNull() {
        // given
        val store = StoreMother.create(autoFill = true)

        // when

        // then
        assertThat(store.displayedName).isEqualTo(store.displayName)
    }

    @DisplayName("새로운 주소가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewAddressIsEmpty(address: String) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable { store.address = address }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 주소가 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewAddressLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.create()
        val address = "x".repeat(Store.MAX_ADDRESS_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.address = address }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("정상적인 경우 객체가 생성된다")
    @Test
    fun createInstantWhenValidInput() {
        // given
        val name = "validName"

        // when
        val category = StoreMother.create { it.name = name }

        // then
        assertThat(category).isNotNull
    }

    @DisplayName("새로운 카테고리를 추가할 수 있다")
    @Test
    fun addNewCategory() {
        // given
        val store = StoreMother.create()
        val category = Category("sample")
        ReflectionTestUtils.setField(category, "id", 1L)

        // when
        store.addCategory(category)

        // then
        assertThat(store.getCategories()).contains(category)
    }

    @DisplayName("이미 존재하는 카테고리는 중복 저장되지 않는다")
    @Test
    fun notAddNewCategoryWhenAlreadyContains() {
        // given
        val category = Category("sample")
        val store = StoreMother.create { it.addCategory(category) }
        val prevCategoryCount = store.getCategories().size

        // when
        store.addCategory(category)

        // then
        assertThat(store.getCategories().size).isEqualTo(prevCategoryCount)
    }

    @DisplayName("새로운 운영시간를 추가할 수 있다")
    @Test
    fun addNewStoreHours() {
        // given
        val store = StoreMother.createWithId()
        val storeHours = StoreHoursMother.create()

        // when
        store.addStoreHour(storeHours)

        // then
        assertThat(
            store.getStoreHours().any {
                it.dayOfWeek == storeHours.dayOfWeek &&
                    it.openAt == storeHours.openAt &&
                    it.closeAt == storeHours.closeAt
            }
        ).isTrue()
    }

    @DisplayName("새로운 이미지 주소가 빈 값일 경우 예외를 던진다")
    @ParameterizedTest
    @EmptySource
    fun throwExceptionWhenNewImageAddressIsEmpty(newImageAddress: String) {
        // given
        val store = StoreMother.create()

        // when
        val throwable = catchThrowable { store.imageAddress = newImageAddress }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 주소가 최소 길이 이상일 경우 에외를 던진다")
    @Test
    fun throwExceptionWhenNewImageAddressLengthGreaterThanMaxLength() {
        // given
        val store = StoreMother.create()
        val newImageAddress = "x".repeat(Store.MAX_IMAGE_ADDRESS_LENGTH + 1)

        // when
        val throwable = catchThrowable { store.imageAddress = newImageAddress }

        // then
        assertThat(throwable).isInstanceOf(RuntimeException::class.java)
    }

    @DisplayName("새로운 연락처를 추가할 수 있다")
    @Test
    fun addNewPhoneNumber() {
        // given
        val store = StoreMother.createWithId()
        val newPhoneNumber = StoreMother.PHONE_NUMBER

        // when
        store.phoneNumber = newPhoneNumber

        // then
        assertThat(store.phoneNumber).isEqualTo(newPhoneNumber)
    }

    @DisplayName("새로운 이미지 주소를 추가할 수 있다")
    @Test
    fun addNewImageAddress() {
        // given
        val store = StoreMother.createWithId()
        val newImageAddress = StoreMother.IMAGE_ADDRESS

        // when
        store.imageAddress = newImageAddress

        // then
        assertThat(store.imageAddress).isEqualTo(newImageAddress)
    }

    @DisplayName("새로운 위치 정보를 추가할 수 있다")
    @Test
    fun addNewLocation() {
        // given
        val store = StoreMother.createWithId()
        val newLocation = StoreMother.LOCATION

        // when
        store.location = newLocation

        // then
        assertThat(store.location).isEqualTo(newLocation)
    }

    @DisplayName("이미 존재하는 시간과 겹친다면 운영시간은 추가되지 않는다: 같은 날짜")
    @Test
    fun throwExceptionWhenNewSameDayOfWeekStoreHoursOverlapTime() {
        // given
        val storeHours = StoreHoursMother.create()
        val store = StoreMother.create { it.addStoreHour(storeHours) }

        // when
        val throwable = catchThrowable { store.addStoreHour(storeHours) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("이미 존재하는 시간과 겹친다면 운영시간은 추가되지 않는다: 하루 전 새벽 운영")
    @Test
    fun throwExceptionWhenNewPrevDayOfWeekStoreHoursOverlapTime() {
        // given
        val currentStoreHours = StoreHoursMother.create()
        val store = StoreMother.create(autoFill = true) { it.addStoreHour(currentStoreHours) }
        val newStoreHours = StoreHours(
            DayOfWeek.of(currentStoreHours.dayOfWeek.ordinal - 1),
            openAt = StoreHours.MIN_TIME_MINUTE,
            closeAt = currentStoreHours.openAt + StoreHours.ONE_DAY_MINUTE + 1
        )

        // when
        val throwable = catchThrowable { store.addStoreHour(newStoreHours) }

        // then
        assertThat(throwable).isNotNull()
    }

    @DisplayName("이미 존재하는 시간과 겹친다면 운영시간은 추가되지 않는다: 기존 시간이 새벽운영")
    @Test
    fun throwExceptionWhenNewNextDayOfWeekStoreHoursOverlapTime() {
        // given
        val currentStoreHours = StoreHoursMother.create { it.closeAt = StoreHours.MAX_TIME_MINUTE }
        val store = StoreMother.create { it.addStoreHour(currentStoreHours) }
        val newStoreHours = StoreHours(
            DayOfWeek.of(currentStoreHours.dayOfWeek.ordinal + 1),
            openAt = currentStoreHours.closeAt - StoreHours.ONE_DAY_MINUTE,
            closeAt = StoreHours.MAX_TIME_MINUTE
        )

        // when
        val throwable = catchThrowable { store.addStoreHour(newStoreHours) }

        // then
        assertThat(throwable).isNotNull()
    }
}
