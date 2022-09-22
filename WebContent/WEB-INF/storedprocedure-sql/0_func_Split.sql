IF OBJECT_ID('dbo.func_split') IS NOT NULL
	drop FUNCTION [dbo].[func_split] 

GO

CREATE FUNCTION [dbo].[func_split] 
    (   
    @DelimitedString    varchar(8000),
	@DelimitedString2    varchar(8000),
    @Delimiter              varchar(100) 
    )
RETURNS @tblArray TABLE
    (
    ElementID   int IDENTITY(1,1),  -- Array index
    TargetElement     varchar(1000),               -- Array element contents
	SourceElement     varchar(1000)               -- Array element contents
    )
AS
BEGIN

    -- Local Variable Declarations
    -- ---------------------------
    DECLARE @Index      smallint,
			@Index2      smallint,
            @Start      smallint,
			@Start2      smallint,
            @DelSize    smallint

    SET @DelSize = LEN(@Delimiter)

    -- Loop through source string and add elements to destination table array
    -- ----------------------------------------------------------------------
    WHILE LEN(@DelimitedString) > 0
    BEGIN

        SET @Index = CHARINDEX(@Delimiter, @DelimitedString)
		SET @Index2 = CHARINDEX(@Delimiter, @DelimitedString2)

        IF @Index = 0
            BEGIN

                INSERT INTO
                    @tblArray 
                    (TargetElement, SourceElement)
                VALUES
                    (LTRIM(RTRIM(@DelimitedString)),LTRIM(RTRIM(@DelimitedString2)))

                BREAK
            END
        ELSE
            BEGIN

                INSERT INTO
                    @tblArray 
                    (TargetElement, SourceElement)
                VALUES
                    (LTRIM(RTRIM(SUBSTRING(@DelimitedString, 1,@Index - 1))),LTRIM(RTRIM(SUBSTRING(@DelimitedString2, 1,@Index2 - 1))))

		

                SET @Start = @Index + @DelSize
				SET @Start2 = @Index2 + @DelSize
				SET @DelimitedString = SUBSTRING(@DelimitedString, @Start , LEN(@DelimitedString) - @Start + 1)
				SET @DelimitedString2 = SUBSTRING(@DelimitedString2, @Start2 , LEN(@DelimitedString2) - @Start2 + 1)

				--SET @Start = 3
                --SET @DelimitedString = 'dsfasdfsdf'
            END
    END

    RETURN
END