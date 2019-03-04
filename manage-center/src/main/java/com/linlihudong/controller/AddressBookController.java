package com.linlihudong.controller;

import cn.com.bmsoft.common.BaseController;
import com.linlihudong.bean.AddressBook;
import com.linlihudong.service.AddressBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import cn.com.bmsoft.common.util.RestResultGenerator;
import cn.com.bmsoft.common.bean.ResponseResult;
import cn.com.bmsoft.common.page.PageDto;
import cn.com.bmsoft.common.dto.DeleteInDto;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import com.linlihudong.domain.requestdto.AddressBookInDTO;
import com.linlihudong.domain.dto.responsedto.AddressBookOutDTO;


/**
 * 通讯录
 *
 * @author kevin
 * @date 2019年03月04日 16:28:13
 */
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("addressBook")
@Api(value = "AddressBookController", tags = {"通讯录"})
public class AddressBookController extends BaseController {

    private final AddressBookService addressBookService;

    /**
     * 插入
     * @param addressBookInDTO 入参实体
     * @return ResponseResult
     */
    @PostMapping(value = "save")
    public ResponseResult<Long> save(@RequestBody AddressBookInDTO  addressBookInDTO) throws Exception{
        return RestResultGenerator.genSuccessResult(addressBookService.save(addressBookInDTO));
    }

    /**
     * 查询列表(分页)
     * @param addressBookInDTO 入参实体
     * @return ResponseResult
     */
    @PostMapping(value = "getAddressBookList")
    public ResponseResult<PageDto<AddressBookOutDTO>> getAddressBookList(@RequestBody AddressBookInDTO addressBookInDTO) throws Exception{
        return RestResultGenerator.genSuccessResult(addressBookService.getAddressBookList(addressBookInDTO));
    }


    /**
     * 更新
     * @param addressBookInDTO
     * @return ResponseResult
     */
    @PutMapping(value = "update")
    public ResponseResult<Long> update(@RequestBody  AddressBookInDTO addressBookInDTO) throws Exception{
        return RestResultGenerator.genSuccessResult(addressBookService.update(addressBookInDTO));
    }


    /**
     * 主键查询
     * @param id id
     * @return ResponseResult
     */
    @ResponseBody
    @GetMapping(value = "{id}")
    public ResponseResult<AddressBookOutDTO> selectById(@PathVariable("id") String id) throws Exception{
        return RestResultGenerator.genSuccessResult(addressBookService.selectById(id));
    }

    /**
     * 根据id集合删除记录
     * @param ids id集合
     * @return ResponseResult
     */
    @PutMapping(value = "delete")
    public ResponseResult<Integer> delete(@RequestBody DeleteInDto ids) throws Exception{
        return RestResultGenerator.genSuccessResult(addressBookService.delete(ids));
    }
}
