
-- V1.0.2   2018/06/06 加入delete時會更新LastUpdate
-- V1.0.3   2018/06/06 修正只有PK key但無ColumnList 會導致無法順利執行的錯誤
-- V1.0.4   2018/06/11 加入@LinkServerDB  
--                     MARK 大寫變小寫
-- V1.0.5   2018/06/14 加入try catch
-- V1.0.6   2018/06/15 加入DATASET skip (未納入使用)
-- V1.0.7   2018/07/05 避免SourceTable資料會重複沒有唯一值，加入distinct
-- V1.0.8   2018/08/06 查詢使否有change_state欄位，如果有就要過濾掉delete的資料
IF OBJECT_ID('dbo.CompareTables_Openquery') IS NOT NULL
	drop PROCEDURE [dbo].[CompareTables_Openquery];

GO

CREATE  PROCEDURE [dbo].[CompareTables_Openquery](
	 @LinkServerName varchar(max),			--Oracle來源
	 @LinkServerDB varchar(max),			--Oracle來源
	 @jobStatus varchar(max),				--執行動作Tag(new、update、delete、view)
											--new    :新增資料
											--update :新增資料 > 更新資料
											--delete :新增資料 > 更新資料 > 刪除資料 
											--view   :檢視異動欄位狀況

	 @TargetTable Varchar(max),				--目標Table
	 @TargetTablePKkey  varchar(max),		--目標Table PK	
	 @TargetTableColumnList varchar(max),	--目標Table 欄位(不可包含PK、不需放入LAST_UPDATE_TIMESTAMP)

	 @SourceTable varchar(max),				--來源Table
	 @SourceTablePKkey  varchar(max),		--來源Table PK
	 @SourceTableColumnList varchar(max),	--來源Table 欄位(不可包含PK、不需放入LAST_UPDATE_TIMESTAMP)
	 @EmpNo int OUTPUT)						--輸出總異動資料筆數

AS




-- 宣告變數
declare @SQL varchar(8000);
declare @DeleteSQL varchar(8000);
declare @SQL_view varchar(8000);
declare @SQL_new varchar(8000);
declare @SQL_modified varchar(8000);
declare @SQL_delete varchar(8000);

DECLARE @SQLCommand_view nvarchar(max)
DECLARE @SQLCommand_new nvarchar(max)
DECLARE @SQLCommand_modified nvarchar(max)
DECLARE @SQLCommand_delete nvarchar(max)


DECLARE @SourceTableColumnList_All varchar(8000);
DECLARE @TargetTableColumnList_All varchar(8000);



IF (@SourceTableColumnList = '')
BEGIN
	set @SourceTableColumnList_All = @SourceTablePKkey
	set @TargetTableColumnList_All = @TargetTablePKkey
END
ELSE
BEGIN
	set @SourceTableColumnList_All = @SourceTablePKkey + ' , '+@SourceTableColumnList
	set @TargetTableColumnList_All = @TargetTablePKkey + ' , '+@TargetTableColumnList
END

DECLARE  @first_TargetTablePKkey Varchar(MAX)
DECLARE  @first_SourceTablePKkey Varchar(MAX)

--Find fist pk key
select @first_TargetTablePKkey=TargetElement FROM dbo.func_split(''+@TargetTablePKkey+'',''+@SourceTablePKkey+'', ',') where ElementID = 1
select @first_SourceTablePKkey=SourceElement FROM dbo.func_split(''+@TargetTablePKkey+'',''+@SourceTablePKkey+'', ',') where ElementID = 1




--組合Insert前置語法2
DECLARE   @Insert_SetColumnList2 varchar(max)=''
select @Insert_SetColumnList2=@Insert_SetColumnList2 +'Atarge.'+SourceElement + ', ' 
							 FROM dbo.func_split(''+@TargetTableColumnList_All+'',''+@SourceTableColumnList_All+'', ',')

select @Insert_SetColumnList2= LEFT(@Insert_SetColumnList2, Len(@Insert_SetColumnList2)-1);


--組合update前置語法 for update
DECLARE   @Update_SetColumnList varchar(max)=''

--組合update前置語法 for delete
DECLARE   @Update_SetColumnList_delete varchar(max)=''

IF (@SourceTableColumnList= '')
BEGIN
	 print '無ColumnList欄位';
END
ELSE
BEGIN
	select @Update_SetColumnList= @Update_SetColumnList + 
							  @TargetTable +'.'+ SourceElement + ' = B.'+ TargetElement + ', ' 
							  FROM dbo.func_split(''+@SourceTableColumnList+'',''+@TargetTableColumnList+'', ',')

	select @Update_SetColumnList= LEFT(@Update_SetColumnList, Len(@Update_SetColumnList)-1);


	select @Update_SetColumnList_delete= @Update_SetColumnList_delete + 
								  @TargetTable +'.'+ SourceElement + ' = B.'+ SourceElement + ', ' 
								  FROM dbo.func_split(''+@SourceTableColumnList+'',''+@TargetTableColumnList+'', ',')

	select @Update_SetColumnList_delete= LEFT(@Update_SetColumnList_delete, Len(@Update_SetColumnList_delete)-1);
END


--組合Delete前置語法-用於Union表格
DECLARE   @delete_SetColumnList varchar(max)=''
select @delete_SetColumnList=@delete_SetColumnList +'B.'+TargetElement + ', ' 
							 FROM dbo.func_split(''+@TargetTableColumnList_All+'',''+@SourceTableColumnList_All+'', ',')

select @delete_SetColumnList= LEFT(@delete_SetColumnList, Len(@delete_SetColumnList)-1);




--組合on語法
DECLARE   @on_SetColumnList varchar(max)=''
select @on_SetColumnList=@on_SetColumnList +'Atarge.'+SourceElement + ' = B.' +  TargetElement +' and '
							 FROM dbo.func_split(''+@TargetTablePKkey+'',''+@SourceTablePKkey+'', ',')

select @on_SetColumnList= LEFT(@on_SetColumnList, Len(@on_SetColumnList)-3);


DECLARE   @on_SetColumnList_2 varchar(max)=''
select @on_SetColumnList_2=@on_SetColumnList_2 +@TargetTable+'.'+TargetElement + ' = B.' + SourceElement  +' and '
							 FROM dbo.func_split(''+@TargetTablePKkey+'',''+@SourceTablePKkey+'', ',')

select @on_SetColumnList_2= LEFT(@on_SetColumnList_2, Len(@on_SetColumnList_2)-3);


--查詢使否有change_state欄位，如果有就要過濾掉delete的資料
DECLARE   @check_ChangeState varchar(max)=''
select @check_ChangeState = SourceElement  FROM dbo.func_split(''+@TargetTableColumnList_All+'',''+@SourceTableColumnList_All+'', ',')  as a where a.SourceElement = 'change_state'

DECLARE  @filter_ChangeState varchar(max)=''
IF @check_ChangeState  = 'change_state' SET @filter_ChangeState = ' and change_state <> ''''delete'''' OR change_state IS NULL '





--比較出差異資料
set @SQL = 'SELECT * FROM OPENQUERY(['+@LinkServerName+'],''SELECT  distinct ''''SourceTable '''' AS TableName,'+@SourceTableColumnList_All+'  FROM '+ @LinkServerDB+@SourceTable   + ' where (1=1) '+  @filter_ChangeState + ''')'+
		   ' UNION ALL SELECT ''TargetTable'' As TableName, ' +  @TargetTableColumnList_All + ' FROM ' + @TargetTable 

--set @SQL = 'SELECT  ''SourceTable '' AS TableName,* from ( ' + @SourceTable + ' ) as A '+
--		   ' UNION ALL SELECT ''TargetTable'' As TableName, ' +  @TargetTableColumnList_All + ' FROM ' + @TargetTable

--過濾被刪除的資料 不等於0 或是 null
set @SQL = @SQL + '  where mark <> 0 or mark is null '

set @DeleteSQL = 'SELECT Max(TableName) as TableName, ' + @SourceTablePKkey +
		   ' FROM (' + @SQL + ') A GROUP BY ' + @SourceTablePKkey +
           ' HAVING COUNT(*) = 1'

set @SQL = 'SELECT Max(TableName) as TableName, ' + @SourceTableColumnList_All +
		   ' FROM (' + @SQL + ') A GROUP BY ' + @SourceTableColumnList_All +
           ' HAVING COUNT(*) = 1'


--出找新增的資料並更新(含 CREATE_DATE, LAST_UPDATE_TIMESTAMP)
set  @SQL_new = 
		'insert into ' + @TargetTable + ' ('+ @TargetTableColumnList_All+  ', CREATE_DATE, LAST_UPDATE_TIMESTAMP' +')' + 
		'select '+ @Insert_SetColumnList2+',GETDATE() ,GETDATE() ' +
		'from(' +
			@SQL
			+
		' )as Atarge'+ 
		' LEFT JOIN '+@TargetTable+ ' B ON ' + @on_SetColumnList +	
		' WHERE B.'+@first_TargetTablePKkey+' IS NULL'


declare @smark varchar(8000);
IF (@Update_SetColumnList_delete= '')
BEGIN
	set @smark = ''
END
ELSE
BEGIN
	set @smark = ', '
END




--出找需要更新的資料並更新(含 LAST_UPDATE_TIMESTAMP) 加入MARK
set @SQL_modified =
'UPDATE '+ @TargetTable +' SET '+ @Update_SetColumnList + @smark +@TargetTable +'.LAST_UPDATE_TIMESTAMP = GETDATE(),'+@TargetTable +'.mark = 1 '+
'FROM'+
	'(SELECT Atarge.*, ''MODIFIED''  AS ''CHANGE_TYPE'''+
		'FROM (' +
				@SQL
				+
				' and Max(TableName) = ''SourceTable'''+
		   ' ) Atarge '+
		' INNER JOIN '+@TargetTable+' B ON '+ @on_SetColumnList +')as B ' +
'WHERE '+@on_SetColumnList_2 


set @SQL_delete = 
	'UPDATE '+ @TargetTable +' SET '+ @Update_SetColumnList_delete +@smark+@TargetTable +'.LAST_UPDATE_TIMESTAMP = GETDATE(),'+@TargetTable+'.mark = 0'  +' '+
	'FROM '+ @TargetTable + ', ( '+
		' select Atarge.TableName,'+ @delete_SetColumnList +' ,''DELETED'' AS ''CHANGE_TYPE''' + ' ,B.mark'+
			' from ' +@TargetTable +	' B'+ 
			' LEFT JOIN (' +
					@DeleteSQL
					+ 
					' and Max(TableName) = ''TargetTable'''+
				' ) Atarge '+	
			' ON ' + @on_SetColumnList+
			' WHERE Atarge.'+@first_SourceTablePKkey+' IS not NULL ' +
	' ) as B ' +
	'WHERE '+@on_SetColumnList_2
		


-- 動態組出 T-SQL
SET @SQLCommand_new = @SQL_new 
SET @SQLCommand_modified = @SQL_modified 
SET @SQLCommand_delete = @SQL_delete

---------------------------------------------------------------------

BEGIN TRY

IF (@jobStatus = 'new')
	begin	
		exec sp_executesql @SQLCommand_new
		SELECT @EmpNo = @@ROWCOUNT;
		print '新增資料'
	end
else if (@jobStatus = 'update')
	begin
		
		exec sp_executesql @SQLCommand_new
		set @EmpNo = @@ROWCOUNT
		print '新增資料'
		print ''
		print ''		
		exec sp_executesql @SQLCommand_modified
		SELECT @EmpNo = @EmpNo + @@ROWCOUNT;
		print '更新資料'
	end
else if (@jobStatus = 'delete')
	begin
		
		exec sp_executesql @SQLCommand_delete
		set @EmpNo = @@ROWCOUNT
		print '刪除資料'
	    print ''
		print ''
		
		exec sp_executesql @SQLCommand_new
		SELECT @EmpNo = @EmpNo  + @@ROWCOUNT;
		print '新增資料'
		print ''
		print ''
		
		exec sp_executesql @SQLCommand_modified
		set @EmpNo = @EmpNo + @@ROWCOUNT
		print '更新資料'

	end
else
	begin

	set  @SQL_view = 
		
		--NEW
		'select Atarge.*, ''NEW'' AS ''CHANGE_TYPE''' +
		'from(' +
			@SQL
			+
		' )as Atarge'+ 
		' LEFT JOIN '+@TargetTable+ ' B ON ' + @on_SetColumnList +	
		' WHERE B.'+@first_TargetTablePKkey+' IS NULL'

		+' UNION ' +

		--MODIFIED
		'SELECT Atarge.*, ''MODIFIED''  AS ''CHANGE_TYPE'''+
		'FROM (' +
				@SQL
				+
				' and Max(TableName) = ''SourceTable'''+
		   ' ) Atarge '+
		' INNER JOIN '+@TargetTable+' B ON '+ @on_SetColumnList

		+' UNION ' +

		--DELETE
		'select Atarge.TableName,'+ @delete_SetColumnList +' ,''DELETED'' AS ''CHANGE_TYPE''' +
		'from ' +@TargetTable +	' B'+ 
		' LEFT JOIN (' +
		          @DeleteSQL
				  +
				' and Max(TableName) = ''TargetTable'''+ 
		  ' ) Atarge '+	
		'ON ' + @on_SetColumnList+
		' WHERE Atarge.'+@first_SourceTablePKkey+' IS not NULL'



		SET @SQLCommand_view = @SQL_view
		exec sp_executesql @SQLCommand_view
		SELECT @EmpNo = @@ROWCOUNT;

	end





END TRY	
BEGIN CATCH
    DECLARE @ErrorMessage  As  VARCHAR(1000)= ERROR_MESSAGE()
    DECLARE @ErrorSeverity As Numeric = ERROR_SEVERITY()
    DECLARE @ErrorState As Numeric   = ERROR_STATE()
    RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
	--PRINT 'ErrorMessage:'+@ErrorMessage+' ErrorSeverity:'+@ErrorSeverity+' ErrorState:'+@ErrorState;

	--DB查詢用
	--SELECT ERROR_NUMBER() AS ErrorNumber,
	--	   ERROR_MESSAGE() AS ErrorMessage,
	--	   ERROR_LINE() AS ErrorLine,
	--	   ERROR_PROCEDURE() AS ErrorProcedure,
	--	   ERROR_SEVERITY() AS ErrorSeverity,
	--	   ERROR_STATE() AS ErrorState

--系統拋回訊息用
	--DECLARE @ErrorMessage As VARCHAR(1000) = CHAR(10)+'錯誤代碼：' +CAST(ERROR_NUMBER() AS VARCHAR)
	--										+CHAR(10)+'錯誤訊息：'+	ERROR_MESSAGE()
	--										+CHAR(10)+'錯誤行號：'+	CAST(ERROR_LINE() AS VARCHAR)
	
	--DECLARE @ErrorSeverity As Numeric = ERROR_SEVERITY()
	--DECLARE @ErrorState As Numeric = ERROR_STATE()
	--RAISERROR( @ErrorMessage, @ErrorSeverity, @ErrorState);--回傳錯誤資訊
	----print @ErrorMessage
END CATCH
