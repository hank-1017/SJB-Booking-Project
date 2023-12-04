<template>
       <div>
             <!-- Sale & Revenue Start -->
             <form th:action="@{/hotel/update}" method="post">
                <input type="hidden" name="_method" value="put">
                <div class="container-fluid pt-4 px-4">
                    <div class="row g-4">
                        <div class="col-sm-12 col-md-6">
                            <div class="bg-light rounded h-100 p-5">
                                <h6 class="mb-4">飯店基本資料</h6>

                                <div class="mb-3">
                                    <label for="hotelName" class="form-label">飯店</label>
                                    <input type="text" class="form-control small-input" id="hotelName" name="hotelName"
                                           th:value="${hotel.hotelName}">
                                    <input type="hidden" name="hotelID" th:value="${hotel.hotelID}"/>

                                </div>
                                <div class="mb-3">
                                    <label for="hotelType" class="form-label">飯店類型</label>
                                    <input type="text" class="form-control small-input" id="hotelType" name="hotelType"
                                           th:value="${hotel.hotelType}">
                                </div>
                                <div class="mb-3">
                                    <label for="hotelStar" class="form-label">飯店星級</label>
                                    <select class="form-select mb-3 small-input" aria-label=".form-select-sm example"
                                        id="hotelStar" name="hotelStar">
                                        <option th:text="${hotel.hotelStar}" selected></option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="GUInumber" class="form-label">GUI編號</label>
                                    <input type="text" class="form-control small-input" id="GUInumber" name="GUInumber"
                                           th:value="${hotelDetail.GUInumber}">
                                </div>
                                <div class="mb-3">
                                    <label for="businessName" class="form-label">商號</label>
                                    <input type="text" class="form-control small-input" id="businessName"
                                        name="businessName"  th:value="${hotelDetail.businessName}">
                                </div>
                                <div class="mb-3">
                                    <label for="openYear" class="form-label">開業年份</label>
                                    <input type="text" class="form-control small-input" id="openYear" name="openYear"
                                           th:value="${hotelDetail.openYear}">
                                </div>

                            </div>
                        </div>
                        <div class="col-sm-12 col-md-6">
                            <div class="bg-light rounded h-100 p-5">
                                <!-- <h6 class="mb-4">飯店基本資料</h6> -->

                                <div class="mb-3">
                                    <label for="tel" class="form-label">電話</label>
                                    <input type="text" class="form-control small-input" id="tel" name="tel"
                                           th:value="${hotel.tel}"/>
                                </div>
                                <div class="mb-3">
                                    <label for="country" class="form-label">國家</label>
                                    <input type="text" class="form-control small-input" id="country" name="country"
                                           th:value="${hotel.country}"/>
                                </div>
                                <div class="twzipcode mb-3"><br>
                                    <label for="city" class="form-label">城市</label>
                                    <select data-role="county" name="city" class="form-select mb-3 small-input"
                                        aria-label="Default select example">
                                    </select>
                                    <label for="region" class="form-label">鄉鎮</label>
                                    <select data-role="district" name="region" class="form-select mb-3 small-input"
                                        aria-label="Default select example"></select>
                                    <label for="postalCode" class="form-label small-input">郵遞區號</label>
                                    <input type="text" readonly data-role="zipcode" name="postalCode" placeholder="郵遞區號"
                                        class="form-control small-input" />
                                </div>
                                <div class="mb-3">
                                    <label for="street">街區</label>
                                    <input type="text" name="street" placeholder="街區" id="street"
                                        class="form-control small-input"  th:value="${hotel.street}" />
                                </div>
                                <button type="submit" class="btn btn-warning m-2">修改</button>

                            </div>
                        </div>
                    </div>
                </div>
            </form>
       </div> 
</template>
    
<script setup lang='ts'>
    
</script>
    
<style>
    
</style>